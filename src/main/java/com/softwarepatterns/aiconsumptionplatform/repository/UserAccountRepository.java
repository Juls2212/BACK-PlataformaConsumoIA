package com.softwarepatterns.aiconsumptionplatform.repository;

import com.softwarepatterns.aiconsumptionplatform.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
