package com.softwarepatterns.aiconsumptionplatform.service;

import org.springframework.stereotype.Component;

@Component
public class TokenEstimator {

    public long estimateTokens(String prompt) {
        String trimmedPrompt = prompt == null ? "" : prompt.trim();
        if (trimmedPrompt.isEmpty()) {
            return 0L;
        }

        int promptWords = trimmedPrompt.split("\\s+").length;
        return (promptWords * 12L) + 25L;
    }
}
