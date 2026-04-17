package com.softwarepatterns.aiconsumptionplatform.dto;

import com.softwarepatterns.aiconsumptionplatform.model.PlanType;
import jakarta.validation.constraints.NotNull;

public class UpgradePlanRequest {

    @NotNull
    private Long userId;

    private PlanType targetPlan;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public PlanType getTargetPlan() {
        return targetPlan;
    }

    public void setTargetPlan(PlanType targetPlan) {
        this.targetPlan = targetPlan;
    }
}
