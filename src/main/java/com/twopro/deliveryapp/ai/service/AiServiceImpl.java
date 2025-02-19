package com.twopro.deliveryapp.ai.service;

import com.twopro.deliveryapp.ai.dto.AiResponseDto;
import com.twopro.deliveryapp.ai.dto.SaveAiServiceRequestDto;
import com.twopro.deliveryapp.ai.entity.Ai;
import com.twopro.deliveryapp.ai.exception.AiServiceNotFoundException;
import com.twopro.deliveryapp.ai.repository.AiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final AiRepository aiRepository;

    @Override
    public AiResponseDto saveAiService(SaveAiServiceRequestDto saveAiServiceRequestDto) {
        Ai aiService = aiRepository.saveAiService(Ai.from(saveAiServiceRequestDto));

        return AiResponseDto.from(aiService);
    }

    @Override
    public AiResponseDto findAiServiceById(UUID aiId) {
        Ai aiService = findAiServiceByIdForServer(aiId);

        return AiResponseDto.from(aiService);
    }

    @Override
    // TODO 유저별(가게별) 조회가 가능하도록 해야 함
    public List<AiResponseDto> findAllAiServices() {
        List<Ai> aiServices = aiRepository.findAllAiService();

        return aiServices.stream().map(AiResponseDto::from).toList();
    }

    @Override
    public List<AiResponseDto> findAllAiServiceByFilter(LocalDate startDate, LocalDate endDate, String menuName) {
        return aiRepository.findAllAiServiceByFilter(startDate, endDate, menuName);
    }

    public void deleteAiService(UUID aiId) {
        Ai aiService = findAiServiceByIdForServer(aiId);
        aiService.delete();
    }

    private Ai findAiServiceByIdForServer(UUID aiId) {
        return aiRepository.findAiServiceById(aiId)
                .orElseThrow(() -> new AiServiceNotFoundException("해당 AI 서비스를 찾을 수 없습니다!"));
    }
}
