package com.twopro.deliveryapp.ai.repository;

import com.twopro.deliveryapp.ai.dto.AiResponseDto;
import com.twopro.deliveryapp.ai.entity.Ai;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AiRepository {

    Ai saveAiService(Ai ai);

    Optional<Ai> findAiServiceById(UUID aiId);

    List<Ai> findAllAiService();

    List<AiResponseDto> findAllAiServiceByFilter(LocalDate startDate, LocalDate endDate, String menuName);
}
