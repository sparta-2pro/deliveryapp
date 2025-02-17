package com.twopro.deliveryapp.menu.dto;

import com.twopro.deliveryapp.menu.entity.MenuStatus;

public record UpdateMenuRequestDto(
        String name,
        MenuStatus status,
        String imageUrl,
        String description,
        int price
) {}
