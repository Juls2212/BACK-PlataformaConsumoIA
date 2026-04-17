package com.softwarepatterns.aiconsumptionplatform.service;

import com.softwarepatterns.aiconsumptionplatform.dto.GenerationRequest;
import com.softwarepatterns.aiconsumptionplatform.dto.GenerationResponse;
import com.softwarepatterns.aiconsumptionplatform.exception.QuotaExceededException;
import com.softwarepatterns.aiconsumptionplatform.exception.ResourceNotFoundException;
import com.softwarepatterns.aiconsumptionplatform.model.PlanType;
import com.softwarepatterns.aiconsumptionplatform.model.QuotaUsage;
import com.softwarepatterns.aiconsumptionplatform.model.UsageHistory;
import com.softwarepatterns.aiconsumptionplatform.model.UserAccount;
import com.softwarepatterns.aiconsumptionplatform.repository.QuotaUsageRepository;
import com.softwarepatterns.aiconsumptionplatform.repository.UsageHistoryRepository;
import com.softwarepatterns.aiconsumptionplatform.repository.UserAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class QuotaProxyService implements AIGenerationService {

    private final AIGenerationService delegate;
    private final UserAccountRepository userAccountRepository;
    private final QuotaUsageRepository quotaUsageRepository;
    private final UsageHistoryRepository usageHistoryRepository;
    private final TokenEstimator tokenEstimator;

    public QuotaProxyService(
            @Qualifier("mockAIGenerationService") AIGenerationService delegate,
            UserAccountRepository userAccountRepository,
            QuotaUsageRepository quotaUsageRepository,
            UsageHistoryRepository usageHistoryRepository,
            TokenEstimator tokenEstimator) {
        this.delegate = delegate;
        this.userAccountRepository = userAccountRepository;
        this.quotaUsageRepository = quotaUsageRepository;
        this.usageHistoryRepository = usageHistoryRepository;
        this.tokenEstimator = tokenEstimator;
    }

    @Override
    @Transactional
    public GenerationResponse generate(GenerationRequest request) {
        UserAccount user = userAccountRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        QuotaUsage quotaUsage = quotaUsageRepository.findByUser_Id(user.getId())
                .orElseGet(() -> quotaUsageRepository.save(new QuotaUsage(user, 0L, LocalDate.now().withDayOfMonth(1))));

        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);
        if (!quotaUsage.getMonthReference().equals(currentMonth)) {
            quotaUsage.setMonthReference(currentMonth);
            quotaUsage.setTokensUsed(0L);
        }

        PlanType currentPlan = user.getCurrentPlan();
        long estimatedTokens = tokenEstimator.estimateTokens(request.getPrompt());
        if (!currentPlan.isUnlimited() && quotaUsage.getTokensUsed() + estimatedTokens > currentPlan.getMonthlyTokenLimit()) {
            throw new QuotaExceededException("Monthly token quota exceeded for user id: " + user.getId());
        }

        GenerationResponse response = delegate.generate(request);

        quotaUsage.setTokensUsed(quotaUsage.getTokensUsed() + response.getTokensConsumed());
        quotaUsageRepository.save(quotaUsage);

        usageHistoryRepository.save(new UsageHistory(
                user,
                request.getPrompt(),
                response.getGeneratedText(),
                response.getTokensConsumed(),
                response.getRequestAcceptedAt(),
                currentPlan.name()
        ));

        response.setCurrentPlan(currentPlan.name());
        if (currentPlan.isUnlimited()) {
            response.setRemainingMonthlyTokens(-1L);
        } else {
            response.setRemainingMonthlyTokens(currentPlan.getMonthlyTokenLimit() - quotaUsage.getTokensUsed());
        }
        return response;
    }
}
