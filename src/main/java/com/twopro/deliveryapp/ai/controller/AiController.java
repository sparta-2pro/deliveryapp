package com.twopro.deliveryapp.ai.controller;

import com.twopro.deliveryapp.ai.dto.AiResponseDto;
import com.twopro.deliveryapp.ai.dto.CreateDescriptionRequestDto;
import com.twopro.deliveryapp.ai.dto.CreateDescriptionResponseDto;
import com.twopro.deliveryapp.ai.service.AiService;
import com.twopro.deliveryapp.common.dto.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai")
public class AiController {

    private final AiService aiService;

    @PostMapping("/description")
    public SingleResponse<CreateDescriptionResponseDto> getAiDescription(
            @RequestBody CreateDescriptionRequestDto requestDto
    ) {

        return SingleResponse.success(aiService.generateDescription(requestDto));
    }

    @GetMapping("/{aiId}")
    public SingleResponse<AiResponseDto> findAiServiceById(@PathVariable UUID aiId) {
        return SingleResponse.success(aiService.findAiServiceById(aiId));
    }

    @GetMapping
    public SingleResponse<List<AiResponseDto>> findAllAiServices(@RequestParam UUID storeId) {
        return SingleResponse.success(aiService.findAllAiServicesByStoreId(storeId));
    }

    @PatchMapping("/{aiId}")
    public void updateDescriptionToAiAnswer(@PathVariable UUID aiId) {
        aiService.updateDescriptionToAiAnswer(aiId);
    }

    @PatchMapping("/delete/{aiId}")
    public void deleteAiServiceById(@PathVariable UUID aiId) {
        aiService.deleteAiServiceById(aiId);
    }
}
