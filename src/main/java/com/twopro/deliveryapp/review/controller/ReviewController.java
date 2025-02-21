package com.twopro.deliveryapp.review.controller;

import com.twopro.deliveryapp.review.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    @PostMapping
    public ResponseEntity<String> createReview(@RequestParam Long userId, @PathVariable UUID orderId) {
        reviewService.createReview(userId, orderId);
        return ResponseEntity.ok("리뷰가 성공적으로 등록되었습니다.");
    }
}
