package com.twopro.deliveryapp.review.service;

import com.twopro.deliveryapp.review.entity.Review;
import com.twopro.deliveryapp.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public void createReview(Long userId, UUID orderId) {

    }

    @Override
    public Review findById(UUID reviewId){
        return reviewRepository.findById(reviewId).orElseThrow();
    }
}
