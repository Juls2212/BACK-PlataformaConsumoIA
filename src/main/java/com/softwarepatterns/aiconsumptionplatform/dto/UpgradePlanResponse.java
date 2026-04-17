package com.softwarepatterns.aiconsumptionplatform.dto;

public class UpgradePlanResponse {

    private Long userId;
    private String previousPlan;
    private String currentPlan;
    private String message;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPreviousPlan() {
        return previousPlan;
    }

    public void setPreviousPlan(String previousPlan) {
        this.previousPlan = previousPlan;
    }

    public String getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(String currentPlan) {
        this.currentPlan = currentPlan;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
