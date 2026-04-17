package com.softwarepatterns.aiconsumptionplatform.repository;

import com.softwarepatterns.aiconsumptionplatform.model.RateLimitState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateLimitStateRepository extends JpaRepository<RateLimitState, Long> {

    Optional<RateLimitState> findByUser_Id(Long userId);
}
