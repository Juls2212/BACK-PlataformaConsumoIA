package com.softwarepatterns.aiconsumptionplatform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanType currentPlan;

    public UserAccount() {
    }

    public UserAccount(String fullName, String email, PlanType currentPlan) {
        this.fullName = fullName;
        this.email = email;
        this.currentPlan = currentPlan;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PlanType getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(PlanType currentPlan) {
        this.currentPlan = currentPlan;
    }
}
