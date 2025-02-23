package com.twopro.deliveryapp.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ReviewCreateResponseDto {
    private UUID reviewId;
}
