package com.twopro.deliveryapp.review.controller;

import com.twopro.deliveryapp.common.dto.MultiResponse;
import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.review.dto.ReviewCreateRequestDto;
import com.twopro.deliveryapp.review.dto.ReviewCreateResponseDto;
import com.twopro.deliveryapp.review.service.ReviewServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    //리뷰 작성
    @PostMapping
    public ResponseEntity createReview(@RequestParam Long userId, @RequestBody @Valid ReviewCreateRequestDto requestDto) {
        ReviewCreateResponseDto review = reviewService.createReview(userId, requestDto);
        return ResponseEntity.ok(SingleResponse.success(review));
    }

    //리뷰 1개 클릭해서 조회
    @GetMapping
    public ResponseEntity findReview(@RequestParam Long userId, @RequestParam UUID reviewId) {
        return ResponseEntity.ok(SingleResponse.success(reviewService.findOne(userId, reviewId)));
    }


    //작성한 리뷰 전체 조회
    @GetMapping("/list")
    public ResponseEntity findReviews(@RequestParam Long userId,
                                      @RequestParam(required = false) Integer page,
                                      @RequestParam(required = false) Integer size,
                                      @RequestParam(required = false) String sortBy,
                                      @RequestParam(required = false) Boolean isAsc) {

        return ResponseEntity.ok(MultiResponse.success(reviewService.findReviews(userId, page-1, size, sortBy, isAsc)));
    }

    //리뷰 삭제
    @PatchMapping
    public ResponseEntity deleteReview(@RequestParam Long userId, @RequestParam UUID reviewId) {
        reviewService.deleteReview(userId, reviewId);
        return ResponseEntity.ok(SingleResponse.success("success"));
    }

    //가게 리뷰 평점 불러오기
    @GetMapping("/rating")
    public ResponseEntity findStoreRating(@RequestParam UUID storeId) {
        return ResponseEntity.ok(SingleResponse.success(reviewService.findStoreRating(storeId)));
    }

    @GetMapping("/scheduler")
    public ResponseEntity reviewScheduler() {
        reviewService.forcingScheduler();
        return ResponseEntity.ok(SingleResponse.success("success"));
    }
}
