package com.twopro.deliveryapp.ai.dto;

import com.twopro.deliveryapp.ai.entity.Ai;

import java.time.LocalDateTime;

public record AiResponseDto (
        String question,
        String aiAnswer,
        LocalDateTime createdAt
) {

    public static AiResponseDto from(Ai ai) {
        return new AiResponseDto(ai.getQuestion(), ai.getAiAnswer(), ai.getCreatedAt());
    }
}
