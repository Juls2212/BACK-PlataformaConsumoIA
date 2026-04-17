package com.softwarepatterns.aiconsumptionplatform.dto;

import java.time.LocalDate;

public class DailyUsagePointResponse {

    private LocalDate date;
    private long tokensUsed;
    private long requestsMade;

    public DailyUsagePointResponse() {
    }

    public DailyUsagePointResponse(LocalDate date, long tokensUsed, long requestsMade) {
        this.date = date;
        this.tokensUsed = tokensUsed;
        this.requestsMade = requestsMade;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getTokensUsed() {
        return tokensUsed;
    }

    public void setTokensUsed(long tokensUsed) {
        this.tokensUsed = tokensUsed;
    }

    public long getRequestsMade() {
        return requestsMade;
    }

    public void setRequestsMade(long requestsMade) {
        this.requestsMade = requestsMade;
    }
}
