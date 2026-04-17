package com.softwarepatterns.aiconsumptionplatform.repository;

import com.softwarepatterns.aiconsumptionplatform.model.UsageHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UsageHistoryRepository extends JpaRepository<UsageHistory, Long> {

    List<UsageHistory> findByUser_IdAndAcceptedAtBetweenOrderByAcceptedAtAsc(Long userId, LocalDateTime start, LocalDateTime end);
}
