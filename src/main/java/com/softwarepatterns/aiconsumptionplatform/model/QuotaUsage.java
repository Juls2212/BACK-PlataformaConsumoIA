package com.softwarepatterns.aiconsumptionplatform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "quota_usage")
public class QuotaUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserAccount user;

    @Column(nullable = false)
    private long tokensUsed;

    @Column(nullable = false)
    private LocalDate monthReference;

    public QuotaUsage() {
    }

    public QuotaUsage(UserAccount user, long tokensUsed, LocalDate monthReference) {
        this.user = user;
        this.tokensUsed = tokensUsed;
        this.monthReference = monthReference;
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

    public long getTokensUsed() {
        return tokensUsed;
    }

    public void setTokensUsed(long tokensUsed) {
        this.tokensUsed = tokensUsed;
    }

    public LocalDate getMonthReference() {
        return monthReference;
    }

    public void setMonthReference(LocalDate monthReference) {
        this.monthReference = monthReference;
    }
}
