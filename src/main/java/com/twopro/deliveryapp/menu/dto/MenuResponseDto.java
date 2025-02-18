package com.twopro.deliveryapp.menu.dto;

import com.twopro.deliveryapp.menu.entity.Menu;
import com.twopro.deliveryapp.menu.entity.MenuStatus;

public record MenuResponseDto(
        String name,
        MenuStatus status,
        String description,
        int price
) {

    public static MenuResponseDto from(Menu menu) {
        return new MenuResponseDto(
                menu.getName(),
                menu.getStatus(),
                menu.getDescription(),
                menu.getPrice()
        );
    }
}
