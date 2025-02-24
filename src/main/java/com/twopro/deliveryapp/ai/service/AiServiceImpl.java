package com.twopro.deliveryapp.ai.service;

import com.twopro.deliveryapp.ai.dto.AiResponseDto;
import com.twopro.deliveryapp.ai.dto.CreateDescriptionRequestDto;
import com.twopro.deliveryapp.ai.dto.CreateDescriptionResponseDto;
import com.twopro.deliveryapp.ai.dto.SaveAiServiceRequestDto;
import com.twopro.deliveryapp.ai.entity.Ai;
import com.twopro.deliveryapp.ai.exception.AiServiceNotFoundException;
import com.twopro.deliveryapp.ai.repository.AiRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final AiRepository aiRepository;
    private final ChatGptClient chatGptClient;

    @Override
    @Transactional
    public CreateDescriptionResponseDto generateDescription(CreateDescriptionRequestDto requestDto) {
        String prompt = generatePrompt(requestDto);

        String generatedDescription = chatGptClient.generateDescription(prompt);
        saveAiService(new SaveAiServiceRequestDto(requestDto.menu(), requestDto.question(), generatedDescription));

        return new CreateDescriptionResponseDto(generatedDescription);
    }

    private String generatePrompt(CreateDescriptionRequestDto requestDto) {
        return String.format(
                        "너는 메뉴 설명을 작성하는 전문가야.\n" +
                        "아래 메뉴에 대한 설명을 작성해줘:\n" +
                        "메뉴 이름: %s\n" +
                        "설명 작성 가이드: %s",
                        requestDto.menu().getName(),
                        requestDto.question()
        );
    }

    @Override
    // TODO 반환 값 사용하는 곳 없을지 고민해보기
    public void saveAiService(SaveAiServiceRequestDto saveAiServiceRequestDto) {
        Ai aiService = aiRepository.saveAiService(Ai.from(saveAiServiceRequestDto));

        AiResponseDto.from(aiService);
    }

    @Override
    public AiResponseDto findAiServiceById(UUID aiId) {
        return AiResponseDto.from(findAiServiceByIdForServer(aiId));
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

    private Ai findAiServiceByIdForServer(UUID aiId) {
        return aiRepository.findAiServiceById(aiId)
                .orElseThrow(() -> new AiServiceNotFoundException("해당 AI 서비스를 찾을 수 없습니다!"));
    }

    @Override
    @Transactional
    public void updateDescriptionToAiAnswer(UUID aiId) {
        Ai findAi = findAiServiceByIdForServer(aiId);
        String answer = findAi.getAiAnswer();

        findAi.setAiAnswer(answer);
    }

    @Override
    @Transactional
    public void deleteAiServiceById(UUID aiId) {
        Ai aiService = findAiServiceByIdForServer(aiId);
        aiService.delete();
    }
}
