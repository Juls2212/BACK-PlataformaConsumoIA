package com.softwarepatterns.aiconsumptionplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AiConsumptionPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiConsumptionPlatformApplication.class, args);
    }
}
