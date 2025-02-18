package com.twopro.deliveryapp.menu.dto;

import com.twopro.deliveryapp.menu.entity.MenuStatus;
import com.twopro.deliveryapp.store.entity.Store;

// 가게 사장이 메뉴 등록 -> 유저 정보, 가게 정보 모두 있음
public record AddMenuRequestDto (
        // TODO StoreResponseDto 받아야 함
        Store store,
        String name,
        MenuStatus status,
        String imageUrl,
        String description,
        int price
) {}
