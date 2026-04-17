package com.softwarepatterns.aiconsumptionplatform.model;

public enum PlanType {
    FREE(10, 50_000L),
    PRO(60, 500_000L),
    ENTERPRISE(Integer.MAX_VALUE, Long.MAX_VALUE);

    private final int requestsPerMinuteLimit;
    private final long monthlyTokenLimit;

    PlanType(int requestsPerMinuteLimit, long monthlyTokenLimit) {
        this.requestsPerMinuteLimit = requestsPerMinuteLimit;
        this.monthlyTokenLimit = monthlyTokenLimit;
    }

    public int getRequestsPerMinuteLimit() {
        return requestsPerMinuteLimit;
    }

    public long getMonthlyTokenLimit() {
        return monthlyTokenLimit;
    }

    public boolean isUnlimited() {
        return this == ENTERPRISE;
    }
}
