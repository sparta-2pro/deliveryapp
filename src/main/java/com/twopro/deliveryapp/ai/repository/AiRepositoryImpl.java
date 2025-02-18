package com.twopro.deliveryapp.ai.repository;

import com.twopro.deliveryapp.ai.dto.CreateAiServiceRequestDto;
import com.twopro.deliveryapp.ai.entity.Ai;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AiRepositoryImpl implements AiRepository {

    private final JpaAiRepository jpaAiRepository;

    @Override
    public Ai createAiService(Ai ai) {
        return jpaAiRepository.save(ai);
    }

    @Override
    public Optional<Ai> findAiServiceById(UUID aiId) {
        return jpaAiRepository.findById(aiId);
    }

    // TODO 유저별 검색되도록 수정해야 함
    @Override
    public List<Ai> findAllAiService() {
        return jpaAiRepository.findAll();
    }

    @Override
    public Optional<List<Ai>> findAllAiServiceByFilter() {
        return Optional.empty();
    }
}
