package com.twopro.deliveryapp.review.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ReviewNotWriteCustomerException extends RuntimeException {
    private Long userId;
    private Long reviewUserId;
    private UUID reviewId;

    public ReviewNotWriteCustomerException(String message, Long userId, Long reviewUserId, UUID reviewId) {
        super(message);
        this.reviewUserId = reviewUserId;
        this.userId = userId;
        this.reviewId = reviewId;
    }
}
