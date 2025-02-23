package com.twopro.deliveryapp.review.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ReviewNotFoundException extends RuntimeException{
    private Long userId;
    private UUID reviewId;

    public ReviewNotFoundException(String message) {
        super(message);
    }

    public ReviewNotFoundException(String message, Long userId, UUID reviewId) {
        super(message);
        this.userId = userId;
        this.reviewId = reviewId;
    }

    public ReviewNotFoundException(String message, UUID reviewId) {
        super(message);
        this.reviewId = reviewId;
    }
}
