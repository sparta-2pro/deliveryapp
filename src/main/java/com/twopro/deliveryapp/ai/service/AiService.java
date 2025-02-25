package com.twopro.deliveryapp.ai.service;

import com.twopro.deliveryapp.ai.dto.AiResponseDto;
import com.twopro.deliveryapp.ai.dto.CreateDescriptionRequestDto;
import com.twopro.deliveryapp.ai.dto.CreateDescriptionResponseDto;
import com.twopro.deliveryapp.ai.dto.SaveAiServiceRequestDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AiService {

    CreateDescriptionResponseDto generateDescription(CreateDescriptionRequestDto requestDto);

    void saveAiService(SaveAiServiceRequestDto saveAiServiceRequestDto);

    AiResponseDto findAiServiceById(UUID aiId);

    List<AiResponseDto> findAllAiServicesByStoreId(UUID storeId, Long size, UUID lastAiId);

    List<AiResponseDto> findAllAiServiceByFilter(
            LocalDate startDate, LocalDate endDate, String menuName, UUID storeId, int size, UUID lastAiId
    );

    void updateDescriptionToAiAnswer(UUID aiId);

    void deleteAiServiceById(UUID aiId);
}
