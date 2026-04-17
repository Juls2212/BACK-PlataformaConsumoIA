package com.softwarepatterns.aiconsumptionplatform.service;

import com.softwarepatterns.aiconsumptionplatform.dto.DailyUsagePointResponse;
import com.softwarepatterns.aiconsumptionplatform.dto.QuotaStatusResponse;
import com.softwarepatterns.aiconsumptionplatform.dto.UpgradePlanRequest;
import com.softwarepatterns.aiconsumptionplatform.dto.UpgradePlanResponse;
import com.softwarepatterns.aiconsumptionplatform.exception.InvalidPlanUpgradeException;
import com.softwarepatterns.aiconsumptionplatform.exception.ResourceNotFoundException;
import com.softwarepatterns.aiconsumptionplatform.model.PlanType;
import com.softwarepatterns.aiconsumptionplatform.model.QuotaUsage;
import com.softwarepatterns.aiconsumptionplatform.model.RateLimitState;
import com.softwarepatterns.aiconsumptionplatform.model.UsageHistory;
import com.softwarepatterns.aiconsumptionplatform.model.UserAccount;
import com.softwarepatterns.aiconsumptionplatform.repository.QuotaUsageRepository;
import com.softwarepatterns.aiconsumptionplatform.repository.RateLimitStateRepository;
import com.softwarepatterns.aiconsumptionplatform.repository.UsageHistoryRepository;
import com.softwarepatterns.aiconsumptionplatform.repository.UserAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuotaManagementService {

    private final UserAccountRepository userAccountRepository;
    private final QuotaUsageRepository quotaUsageRepository;
    private final RateLimitStateRepository rateLimitStateRepository;
    private final UsageHistoryRepository usageHistoryRepository;

    public QuotaManagementService(
            UserAccountRepository userAccountRepository,
            QuotaUsageRepository quotaUsageRepository,
            RateLimitStateRepository rateLimitStateRepository,
            UsageHistoryRepository usageHistoryRepository) {
        this.userAccountRepository = userAccountRepository;
        this.quotaUsageRepository = quotaUsageRepository;
        this.rateLimitStateRepository = rateLimitStateRepository;
        this.usageHistoryRepository = usageHistoryRepository;
    }

    @Transactional
    public QuotaStatusResponse getStatus(Long userId) {
        UserAccount user = getRequiredUser(userId);
        QuotaUsage quotaUsage = getCurrentQuotaUsage(user);
        RateLimitState rateLimitState = getCurrentRateLimitState(user);

        PlanType currentPlan = user.getCurrentPlan();
        QuotaStatusResponse response = new QuotaStatusResponse();
        response.setUserId(user.getId());
        response.setCurrentPlan(currentPlan.name());
        response.setRequestsUsedCurrentMinute(rateLimitState.getRequestCount());
        response.setNextRateLimitResetAt(rateLimitState.getWindowStartedAt().plusMinutes(1));
        response.setNextMonthlyQuotaResetAt(getNextMonthlyQuotaResetAt());
        response.setMonthlyTokensUsed(quotaUsage.getTokensUsed());

        if (currentPlan.isUnlimited()) {
            response.setRequestLimitCurrentMinute(-1);
            response.setRemainingRequestsCurrentMinute(-1);
            response.setMonthlyTokenLimit(-1L);
            response.setRemainingMonthlyTokens(-1L);
        } else {
            response.setRequestLimitCurrentMinute(currentPlan.getRequestsPerMinuteLimit());
            response.setRemainingRequestsCurrentMinute(Math.max(0, currentPlan.getRequestsPerMinuteLimit() - rateLimitState.getRequestCount()));
            response.setMonthlyTokenLimit(currentPlan.getMonthlyTokenLimit());
            response.setRemainingMonthlyTokens(Math.max(0L, currentPlan.getMonthlyTokenLimit() - quotaUsage.getTokensUsed()));
        }

        return response;
    }

    @Transactional
    public List<DailyUsagePointResponse> getHistory(Long userId) {
        UserAccount user = getRequiredUser(userId);
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(6);
        LocalDateTime from = startDate.atStartOfDay();
        LocalDateTime to = today.plusDays(1).atStartOfDay();

        List<UsageHistory> historyEntries = usageHistoryRepository.findByUser_IdAndAcceptedAtBetweenOrderByAcceptedAtAsc(
                user.getId(),
                from,
                to
        );

        Map<LocalDate, DailyUsagePointResponse> pointsByDate = new LinkedHashMap<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);
            pointsByDate.put(date, new DailyUsagePointResponse(date, 0L, 0L));
        }

        for (UsageHistory entry : historyEntries) {
            LocalDate date = entry.getAcceptedAt().toLocalDate();
            DailyUsagePointResponse point = pointsByDate.get(date);
            if (point != null) {
                point.setTokensUsed(point.getTokensUsed() + entry.getTokensConsumed());
                point.setRequestsMade(point.getRequestsMade() + 1);
            }
        }

        return new ArrayList<>(pointsByDate.values());
    }

    @Transactional
    public UpgradePlanResponse upgradePlan(UpgradePlanRequest request) {
        UserAccount user = getRequiredUser(request.getUserId());
        PlanType previousPlan = user.getCurrentPlan();
        PlanType upgradedPlan = determineUpgradedPlan(previousPlan);

        if (request.getTargetPlan() != null && request.getTargetPlan() != upgradedPlan) {
            throw new InvalidPlanUpgradeException("Requested target plan does not match the next allowed upgrade step for user id: " + user.getId());
        }

        user.setCurrentPlan(upgradedPlan);
        userAccountRepository.save(user);

        UpgradePlanResponse response = new UpgradePlanResponse();
        response.setUserId(user.getId());
        response.setPreviousPlan(previousPlan.name());
        response.setCurrentPlan(upgradedPlan.name());
        if (previousPlan == upgradedPlan) {
            response.setMessage("User is already in the highest available plan.");
        } else {
            response.setMessage("Plan upgraded successfully.");
        }
        return response;
    }

    private UserAccount getRequiredUser(Long userId) {
        return userAccountRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    private QuotaUsage getCurrentQuotaUsage(UserAccount user) {
        QuotaUsage quotaUsage = quotaUsageRepository.findByUser_Id(user.getId())
                .orElseGet(() -> quotaUsageRepository.save(new QuotaUsage(user, 0L, LocalDate.now().withDayOfMonth(1))));

        LocalDate currentMonthReference = LocalDate.now().withDayOfMonth(1);
        if (!quotaUsage.getMonthReference().equals(currentMonthReference)) {
            quotaUsage.setMonthReference(currentMonthReference);
            quotaUsage.setTokensUsed(0L);
            quotaUsageRepository.save(quotaUsage);
        }

        return quotaUsage;
    }

    private RateLimitState getCurrentRateLimitState(UserAccount user) {
        RateLimitState rateLimitState = rateLimitStateRepository.findByUser_Id(user.getId())
                .orElseGet(() -> rateLimitStateRepository.save(new RateLimitState(user, 0, LocalDateTime.now())));

        LocalDateTime now = LocalDateTime.now();
        if (!now.isBefore(rateLimitState.getWindowStartedAt().plusMinutes(1))) {
            rateLimitState.setWindowStartedAt(now);
            rateLimitState.setRequestCount(0);
            rateLimitStateRepository.save(rateLimitState);
        }

        return rateLimitState;
    }

    private PlanType determineUpgradedPlan(PlanType currentPlan) {
        return switch (currentPlan) {
            case FREE -> PlanType.PRO;
            case PRO, ENTERPRISE -> PlanType.ENTERPRISE;
        };
    }

    private LocalDateTime getNextMonthlyQuotaResetAt() {
        YearMonth nextMonth = YearMonth.now().plusMonths(1);
        return nextMonth.atDay(1).atStartOfDay();
    }
}
