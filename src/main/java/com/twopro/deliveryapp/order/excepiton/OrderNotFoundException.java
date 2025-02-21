package com.twopro.deliveryapp.order.excepiton;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderNotFoundException extends RuntimeException {
    private Long userId;
    private UUID orderId;

    public OrderNotFoundException(String message, Long userId, UUID orderId) {
        super(message);
        this.userId = userId;
        this.orderId = orderId;
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
