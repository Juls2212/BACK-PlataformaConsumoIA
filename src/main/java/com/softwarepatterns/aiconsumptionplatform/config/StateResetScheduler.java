package com.softwarepatterns.aiconsumptionplatform.config;

import com.softwarepatterns.aiconsumptionplatform.model.QuotaUsage;
import com.softwarepatterns.aiconsumptionplatform.model.RateLimitState;
import com.softwarepatterns.aiconsumptionplatform.repository.QuotaUsageRepository;
import com.softwarepatterns.aiconsumptionplatform.repository.RateLimitStateRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class StateResetScheduler {

    private final RateLimitStateRepository rateLimitStateRepository;
    private final QuotaUsageRepository quotaUsageRepository;

    public StateResetScheduler(RateLimitStateRepository rateLimitStateRepository, QuotaUsageRepository quotaUsageRepository) {
        this.rateLimitStateRepository = rateLimitStateRepository;
        this.quotaUsageRepository = quotaUsageRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void resetRateLimitCounters() {
        List<RateLimitState> states = rateLimitStateRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (RateLimitState state : states) {
            state.setRequestCount(0);
            state.setWindowStartedAt(now);
        }
        rateLimitStateRepository.saveAll(states);
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void resetMonthlyQuotaUsage() {
        List<QuotaUsage> quotaUsages = quotaUsageRepository.findAll();
        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);
        for (QuotaUsage quotaUsage : quotaUsages) {
            quotaUsage.setTokensUsed(0L);
            quotaUsage.setMonthReference(currentMonth);
        }
        quotaUsageRepository.saveAll(quotaUsages);
    }
}
