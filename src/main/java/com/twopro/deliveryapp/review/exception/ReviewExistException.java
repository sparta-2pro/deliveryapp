package com.twopro.deliveryapp.review.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ReviewExistException extends RuntimeException {
    private UUID orderId;
    private Long userId;

    public ReviewExistException(String message, UUID orderId, Long userId) {
        super(message);
        this.orderId = orderId;
        this.userId = userId;
    }

    public ReviewExistException(String message) {
        super(message);
    }




}
