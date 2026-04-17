package com.softwarepatterns.aiconsumptionplatform.repository;

import com.softwarepatterns.aiconsumptionplatform.model.QuotaUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuotaUsageRepository extends JpaRepository<QuotaUsage, Long> {

    Optional<QuotaUsage> findByUser_Id(Long userId);
}
