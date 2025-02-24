package com.twopro.deliveryapp.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ReviewFindStoreRatingResponse {
    private UUID storeId;
    private Double rating;
    private Long ratingCount;
}
