package com.softwarepatterns.aiconsumptionplatform.exception;

public class MonthlyQuotaExceededException extends RuntimeException {

    public MonthlyQuotaExceededException(String message) {
        super(message);
    }
}
