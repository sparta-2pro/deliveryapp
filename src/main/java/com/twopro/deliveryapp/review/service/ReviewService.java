package com.twopro.deliveryapp.review.service;

import com.twopro.deliveryapp.review.dto.ReviewCreateRequestDto;
import com.twopro.deliveryapp.review.dto.ReviewCreateResponseDto;
import com.twopro.deliveryapp.review.dto.ReviewFindResponseDto;
import com.twopro.deliveryapp.review.dto.ReviewFindStoreRatingResponse;
import com.twopro.deliveryapp.review.entity.Review;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ReviewService {
    ReviewCreateResponseDto createReview(Long userId, ReviewCreateRequestDto requestDto);

    Review findById(UUID reviewId);

    ReviewFindResponseDto findOne(Long userId, UUID reviewId);

    void deleteReview(Long userId, UUID reviewId);

    Page<ReviewFindResponseDto> findReviews(Long userId, Integer page, Integer size, String sortBy, Boolean isAsc);

    ReviewFindStoreRatingResponse findStoreRating(UUID storeId);
}
