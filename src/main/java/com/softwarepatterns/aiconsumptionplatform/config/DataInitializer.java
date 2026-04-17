package com.softwarepatterns.aiconsumptionplatform.config;

import com.softwarepatterns.aiconsumptionplatform.model.PlanType;
import com.softwarepatterns.aiconsumptionplatform.model.QuotaUsage;
import com.softwarepatterns.aiconsumptionplatform.model.RateLimitState;
import com.softwarepatterns.aiconsumptionplatform.model.UserAccount;
import com.softwarepatterns.aiconsumptionplatform.repository.QuotaUsageRepository;
import com.softwarepatterns.aiconsumptionplatform.repository.RateLimitStateRepository;
import com.softwarepatterns.aiconsumptionplatform.repository.UserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedData(
            UserAccountRepository userAccountRepository,
            QuotaUsageRepository quotaUsageRepository,
            RateLimitStateRepository rateLimitStateRepository) {
        return args -> {
            if (userAccountRepository.count() > 0) {
                return;
            }

            UserAccount freeUser = userAccountRepository.save(new UserAccount("Free Demo User", "free@demo.com", PlanType.FREE));
            UserAccount proUser = userAccountRepository.save(new UserAccount("Pro Demo User", "pro@demo.com", PlanType.PRO));
            UserAccount enterpriseUser = userAccountRepository.save(new UserAccount("Enterprise Demo User", "enterprise@demo.com", PlanType.ENTERPRISE));

            quotaUsageRepository.save(new QuotaUsage(freeUser, 0L, LocalDate.now().withDayOfMonth(1)));
            quotaUsageRepository.save(new QuotaUsage(proUser, 0L, LocalDate.now().withDayOfMonth(1)));
            quotaUsageRepository.save(new QuotaUsage(enterpriseUser, 0L, LocalDate.now().withDayOfMonth(1)));

            rateLimitStateRepository.save(new RateLimitState(freeUser, 0, LocalDateTime.now()));
            rateLimitStateRepository.save(new RateLimitState(proUser, 0, LocalDateTime.now()));
            rateLimitStateRepository.save(new RateLimitState(enterpriseUser, 0, LocalDateTime.now()));
        };
    }
}
