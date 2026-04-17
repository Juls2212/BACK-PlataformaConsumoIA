package com.softwarepatterns.aiconsumptionplatform.service;

import com.softwarepatterns.aiconsumptionplatform.dto.GenerationRequest;
import com.softwarepatterns.aiconsumptionplatform.dto.GenerationResponse;

public interface AIGenerationService {

    GenerationResponse generate(GenerationRequest request);
}
