package com.twopro.deliveryapp.ai.repository;

import com.twopro.deliveryapp.ai.dto.CreateAiServiceRequestDto;
import com.twopro.deliveryapp.ai.entity.Ai;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AiRepository {

    Ai createAiService(Ai ai);

    Optional<Ai> findAiServiceById(UUID aiId);

    List<Ai> findAllAiService();

    Optional<List<Ai>> findAllAiServiceByFilter();
}
