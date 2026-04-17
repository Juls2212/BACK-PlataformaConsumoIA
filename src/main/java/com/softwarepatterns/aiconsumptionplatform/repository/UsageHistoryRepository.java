package com.softwarepatterns.aiconsumptionplatform.repository;

import com.softwarepatterns.aiconsumptionplatform.model.UsageHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsageHistoryRepository extends JpaRepository<UsageHistory, Long> {
}
