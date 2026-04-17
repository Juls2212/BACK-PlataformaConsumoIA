package com.softwarepatterns.aiconsumptionplatform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "usage_history")
public class UsageHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    @Column(nullable = false, length = 2000)
    private String prompt;

    @Column(nullable = false, length = 4000)
    private String generatedText;

    @Column(nullable = false)
    private long tokensConsumed;

    @Column(nullable = false)
    private LocalDateTime acceptedAt;

    @Column(nullable = false)
    private String planName;

    public UsageHistory() {
    }

    public UsageHistory(UserAccount user, String prompt, String generatedText, long tokensConsumed, LocalDateTime acceptedAt, String planName) {
        this.user = user;
        this.prompt = prompt;
        this.generatedText = generatedText;
        this.tokensConsumed = tokensConsumed;
        this.acceptedAt = acceptedAt;
        this.planName = planName;
    }

    public Long getId() {
        return id;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

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

    public LocalDateTime getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(LocalDateTime acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}
