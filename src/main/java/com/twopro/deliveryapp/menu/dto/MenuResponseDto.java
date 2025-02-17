package com.twopro.deliveryapp.menu.dto;

import com.twopro.deliveryapp.menu.entity.MenuStatus;

public record MenuResponseDto(
        String name,
        MenuStatus status,
        String description,
        int price
) {}
