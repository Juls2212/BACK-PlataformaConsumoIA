package com.softwarepatterns.aiconsumptionplatform.controller;

import com.softwarepatterns.aiconsumptionplatform.dto.GenerationRequest;
import com.softwarepatterns.aiconsumptionplatform.dto.GenerationResponse;
import com.softwarepatterns.aiconsumptionplatform.service.AIGenerationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AIGenerationController {

    private final AIGenerationService aiGenerationService;

    public AIGenerationController(@Qualifier("rateLimitProxyService") AIGenerationService aiGenerationService) {
        this.aiGenerationService = aiGenerationService;
    }

    @PostMapping("/generate")
    public ResponseEntity<GenerationResponse> generate(@Valid @RequestBody GenerationRequest request) {
        return ResponseEntity.ok(aiGenerationService.generate(request));
    }
}
