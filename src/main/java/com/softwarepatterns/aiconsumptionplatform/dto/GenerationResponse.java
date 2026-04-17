package com.softwarepatterns.aiconsumptionplatform.dto;

import java.time.LocalDateTime;

public class GenerationResponse {

    private String generatedText;
    private long tokensConsumed;
    private String currentPlan;
    private long remainingMonthlyTokens;
    private int remainingRequestsThisMinute;
    private LocalDateTime requestAcceptedAt;

    public String getGeneratedText() {
        return generatedText;
    }

    public void setGeneratedText(String generatedText) {
        this.generatedText = generatedText;
    }

    public long getTokensConsumed() {
        return tokensConsumed;
    }

    public void setTokensConsumed(long tokensConsumed) {
        this.tokensConsumed = tokensConsumed;
    }

    public String getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(String currentPlan) {
        this.currentPlan = currentPlan;
    }

    public long getRemainingMonthlyTokens() {
        return remainingMonthlyTokens;
    }

    public void setRemainingMonthlyTokens(long remainingMonthlyTokens) {
        this.remainingMonthlyTokens = remainingMonthlyTokens;
    }

    public int getRemainingRequestsThisMinute() {
        return remainingRequestsThisMinute;
    }

    public void setRemainingRequestsThisMinute(int remainingRequestsThisMinute) {
        this.remainingRequestsThisMinute = remainingRequestsThisMinute;
    }

    public LocalDateTime getRequestAcceptedAt() {
        return requestAcceptedAt;
    }

    public void setRequestAcceptedAt(LocalDateTime requestAcceptedAt) {
        this.requestAcceptedAt = requestAcceptedAt;
    }
}
