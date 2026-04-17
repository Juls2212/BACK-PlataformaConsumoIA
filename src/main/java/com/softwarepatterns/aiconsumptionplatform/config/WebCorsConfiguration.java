package com.softwarepatterns.aiconsumptionplatform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCorsConfiguration implements WebMvcConfigurer {

    private final String allowedOrigin;

    public WebCorsConfiguration(@Value("${ALLOWED_ORIGIN:http://localhost:3000}") String allowedOrigin) {
        this.allowedOrigin = allowedOrigin;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigin)
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowedHeaders("*");

        registry.addMapping("/v3/api-docs/**")
                .allowedOrigins(allowedOrigin)
                .allowedMethods("GET", "OPTIONS")
                .allowedHeaders("*");

        registry.addMapping("/swagger-ui/**")
                .allowedOrigins(allowedOrigin)
                .allowedMethods("GET", "OPTIONS")
                .allowedHeaders("*");
    }
}
