package com.twopro.deliveryapp.order.excepiton;

import lombok.Getter;

@Getter
public class OrderStoreMinPriceException extends RuntimeException {
    private int totalPrice;
    private Long userId;

    public OrderStoreMinPriceException(String message, int totalPrice, Long userId) {
        super(message);
        this.totalPrice = totalPrice;
        this.userId = userId;
    }

}
