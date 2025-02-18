package com.twopro.deliveryapp.ai.dto;

import java.time.LocalDateTime;

public record AiResponseDto (
        String question,
        String aiAnswer,
        LocalDateTime createdAt
) {}
