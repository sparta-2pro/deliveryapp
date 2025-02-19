package com.twopro.deliveryapp.ai.dto;

import com.twopro.deliveryapp.menu.entity.Menu;

public record SaveAiServiceRequestDto(
        // TODO RequestDto 받아야 함
        Menu menu,
        String question,
        String aiAnswer
) {}
