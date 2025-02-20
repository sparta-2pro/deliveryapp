package com.twopro.deliveryapp.ai.dto;

import com.twopro.deliveryapp.menu.entity.Menu;

public record CreateDescriptionRequestDto(
        // TODO dto 받도록 수정해야 함
        Menu menu,
        String question
) {}
