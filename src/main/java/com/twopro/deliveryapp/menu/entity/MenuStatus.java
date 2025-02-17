package com.twopro.deliveryapp.menu.entity;

import lombok.Getter;

@Getter
public enum MenuStatus {
    AVAILABLE("주문 가능"),
    SOLD_OUT("품절"),
    PREPARING("준비 중"),
    DISCONTINUED("판매 중지"),
    LIMITED_QUANTITY("한정 수량");

    private final String description;

    MenuStatus(String description) {
        this.description = description;
    }
}
