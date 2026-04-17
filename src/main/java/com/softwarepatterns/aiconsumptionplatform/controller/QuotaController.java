package com.softwarepatterns.aiconsumptionplatform.controller;

import com.softwarepatterns.aiconsumptionplatform.dto.DailyUsagePointResponse;
import com.softwarepatterns.aiconsumptionplatform.dto.QuotaStatusResponse;
import com.softwarepatterns.aiconsumptionplatform.dto.UpgradePlanRequest;
import com.softwarepatterns.aiconsumptionplatform.dto.UpgradePlanResponse;
import com.softwarepatterns.aiconsumptionplatform.service.QuotaManagementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quota")
public class QuotaController {

    private final QuotaManagementService quotaManagementService;

    public QuotaController(QuotaManagementService quotaManagementService) {
        this.quotaManagementService = quotaManagementService;
    }

    @GetMapping("/status")
    public ResponseEntity<QuotaStatusResponse> getStatus(@RequestParam Long userId) {
        return ResponseEntity.ok(quotaManagementService.getStatus(userId));
    }

    @GetMapping("/history")
    public ResponseEntity<List<DailyUsagePointResponse>> getHistory(@RequestParam Long userId) {
        return ResponseEntity.ok(quotaManagementService.getHistory(userId));
    }

    @PostMapping("/upgrade")
    public ResponseEntity<UpgradePlanResponse> upgradePlan(@Valid @RequestBody UpgradePlanRequest request) {
        return ResponseEntity.ok(quotaManagementService.upgradePlan(request));
    }
}
