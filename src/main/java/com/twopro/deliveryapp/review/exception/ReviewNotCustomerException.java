package com.twopro.deliveryapp.review.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ReviewNotCustomerException extends RuntimeException {
    private UUID orderId;
    private Long userId;
    private Long orderUserId;

    public ReviewNotCustomerException(String message, Long userId, Long orderUserId, UUID orderId) {
        super(message);
        this.userId = userId;
        this.orderId = orderId;
        this.orderUserId = orderUserId;
    }
}
