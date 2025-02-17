package com.twopro.deliveryapp.menu.dto;

import com.twopro.deliveryapp.menu.entity.MenuStatus;

// 가게 사장이 메뉴 등록 -> 유저 정보, 가게 정보 모두 있음
public record AddMenuRequestDto (
        String name,
        MenuStatus status,
        String imageUrl,
        String description,
        int price
) {}
