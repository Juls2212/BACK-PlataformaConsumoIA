package com.softwarepatterns.aiconsumptionplatform.dto;

import java.time.LocalDateTime;

public class QuotaStatusResponse {

    private Long userId;
    private String currentPlan;
    private int requestsUsedCurrentMinute;
    private int requestLimitCurrentMinute;
    private int remainingRequestsCurrentMinute;
    private long monthlyTokensUsed;
    private long monthlyTokenLimit;
    private long remainingMonthlyTokens;
    private LocalDateTime nextRateLimitResetAt;
    private LocalDateTime nextMonthlyQuotaResetAt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(String currentPlan) {
        this.currentPlan = currentPlan;
    }

    public int getRequestsUsedCurrentMinute() {
        return requestsUsedCurrentMinute;
    }

    public void setRequestsUsedCurrentMinute(int requestsUsedCurrentMinute) {
        this.requestsUsedCurrentMinute = requestsUsedCurrentMinute;
    }

    public int getRequestLimitCurrentMinute() {
        return requestLimitCurrentMinute;
    }

    public void setRequestLimitCurrentMinute(int requestLimitCurrentMinute) {
        this.requestLimitCurrentMinute = requestLimitCurrentMinute;
    }

    public int getRemainingRequestsCurrentMinute() {
        return remainingRequestsCurrentMinute;
    }

    public void setRemainingRequestsCurrentMinute(int remainingRequestsCurrentMinute) {
        this.remainingRequestsCurrentMinute = remainingRequestsCurrentMinute;
    }

    public long getMonthlyTokensUsed() {
        return monthlyTokensUsed;
    }

    public void setMonthlyTokensUsed(long monthlyTokensUsed) {
        this.monthlyTokensUsed = monthlyTokensUsed;
    }

    public long getMonthlyTokenLimit() {
        return monthlyTokenLimit;
    }

    public void setMonthlyTokenLimit(long monthlyTokenLimit) {
        this.monthlyTokenLimit = monthlyTokenLimit;
    }

    public long getRemainingMonthlyTokens() {
        return remainingMonthlyTokens;
    }

    public void setRemainingMonthlyTokens(long remainingMonthlyTokens) {
        this.remainingMonthlyTokens = remainingMonthlyTokens;
    }

    public LocalDateTime getNextRateLimitResetAt() {
        return nextRateLimitResetAt;
    }

    public void setNextRateLimitResetAt(LocalDateTime nextRateLimitResetAt) {
        this.nextRateLimitResetAt = nextRateLimitResetAt;
    }

    public LocalDateTime getNextMonthlyQuotaResetAt() {
        return nextMonthlyQuotaResetAt;
    }

    public void setNextMonthlyQuotaResetAt(LocalDateTime nextMonthlyQuotaResetAt) {
        this.nextMonthlyQuotaResetAt = nextMonthlyQuotaResetAt;
    }
}
