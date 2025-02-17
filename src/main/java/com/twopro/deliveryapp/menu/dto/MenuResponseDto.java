package com.twopro.deliveryapp.menu.dto;

import com.twopro.deliveryapp.menu.entity.MenuEntity;
import com.twopro.deliveryapp.menu.entity.MenuStatus;

public record MenuResponseDto(
        String name,
        MenuStatus status,
        String description,
        int price
) {

    public static MenuResponseDto from(MenuEntity menuEntity) {
        return new MenuResponseDto(
                menuEntity.getName(),
                menuEntity.getStatus(),
                menuEntity.getDescription(),
                menuEntity.getPrice()
        );
    }
}
