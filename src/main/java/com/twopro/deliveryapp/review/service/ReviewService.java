package com.twopro.deliveryapp.review.service;

import com.twopro.deliveryapp.review.entity.Review;

import java.util.UUID;

public interface ReviewService {
    void createReview(Long userId, UUID orderId);

    Review findById(UUID reviewId);
}
