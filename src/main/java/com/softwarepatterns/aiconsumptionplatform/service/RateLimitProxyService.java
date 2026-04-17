package com.softwarepatterns.aiconsumptionplatform.service;

import com.softwarepatterns.aiconsumptionplatform.dto.GenerationRequest;
import com.softwarepatterns.aiconsumptionplatform.dto.GenerationResponse;
import com.softwarepatterns.aiconsumptionplatform.exception.RateLimitExceededException;
import com.softwarepatterns.aiconsumptionplatform.exception.ResourceNotFoundException;
import com.softwarepatterns.aiconsumptionplatform.model.PlanType;
import com.softwarepatterns.aiconsumptionplatform.model.RateLimitState;
import com.softwarepatterns.aiconsumptionplatform.model.UserAccount;
import com.softwarepatterns.aiconsumptionplatform.repository.RateLimitStateRepository;
import com.softwarepatterns.aiconsumptionplatform.repository.UserAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class RateLimitProxyService implements AIGenerationService {

    private final AIGenerationService delegate;
    private final UserAccountRepository userAccountRepository;
    private final RateLimitStateRepository rateLimitStateRepository;

    public RateLimitProxyService(
            @Qualifier("quotaProxyService") AIGenerationService delegate,
            UserAccountRepository userAccountRepository,
            RateLimitStateRepository rateLimitStateRepository) {
        this.delegate = delegate;
        this.userAccountRepository = userAccountRepository;
        this.rateLimitStateRepository = rateLimitStateRepository;
    }

    @Override
    @Transactional
    public GenerationResponse generate(GenerationRequest request) {
        UserAccount user = userAccountRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        RateLimitState rateLimitState = rateLimitStateRepository.findByUser_Id(user.getId())
                .orElseGet(() -> rateLimitStateRepository.save(new RateLimitState(user, 0, LocalDateTime.now())));

        PlanType currentPlan = user.getCurrentPlan();
        if (currentPlan.isUnlimited()) {
            GenerationResponse response = delegate.generate(request);
            response.setRemainingRequestsThisMinute(-1);
            return response;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWindow = rateLimitState.getWindowStartedAt().plusMinutes(1);
        if (!now.isBefore(nextWindow)) {
            rateLimitState.setWindowStartedAt(now);
            rateLimitState.setRequestCount(0);
        }

        if (rateLimitState.getRequestCount() >= currentPlan.getRequestsPerMinuteLimit()) {
            long retryAfterSeconds = Math.max(1L, Duration.between(now, rateLimitState.getWindowStartedAt().plusMinutes(1)).getSeconds());
            throw new RateLimitExceededException("Rate limit exceeded for user id: " + user.getId(), retryAfterSeconds);
        }

        rateLimitState.setRequestCount(rateLimitState.getRequestCount() + 1);
        rateLimitStateRepository.save(rateLimitState);

        GenerationResponse response = delegate.generate(request);
        response.setRemainingRequestsThisMinute(currentPlan.getRequestsPerMinuteLimit() - rateLimitState.getRequestCount());
        return response;
    }
}
