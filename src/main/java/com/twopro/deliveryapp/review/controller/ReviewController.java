package com.twopro.deliveryapp.review.controller;

import com.twopro.deliveryapp.common.dto.MultiResponse;
import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.review.dto.ReviewCreateRequestDto;
import com.twopro.deliveryapp.review.dto.ReviewCreateResponseDto;
import com.twopro.deliveryapp.review.service.ReviewServiceImpl;
import com.twopro.deliveryapp.user.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'OWNER')")
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    //리뷰 작성
    @PostMapping
    public ResponseEntity createReview(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody @Valid ReviewCreateRequestDto requestDto) {
        ReviewCreateResponseDto review = reviewService.createReview(userDetails.getUser().getUserId(), requestDto);
        return ResponseEntity.ok(SingleResponse.success(review));
    }

    //리뷰 1개 클릭해서 조회
    @GetMapping
    public ResponseEntity findReview(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam UUID reviewId) {
        return ResponseEntity.ok(SingleResponse.success(reviewService.findOne(userDetails.getUser().getUserId(), reviewId)));
    }


    //작성한 리뷰 전체 조회
    @GetMapping("/list")
    public ResponseEntity findReviews(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestParam(required = false, defaultValue = "1") Integer page,
                                      @RequestParam(required = false, defaultValue = "10") Integer size,
                                      @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                      @RequestParam(required = false, defaultValue = "false") Boolean isAsc) {

        return ResponseEntity.ok(MultiResponse.success(reviewService.findReviews(userDetails.getUser().getUserId(), page-1, size, sortBy, isAsc)));
    }

    //리뷰 삭제
    @PreAuthorize("hasAnyRole('CUSTOMER', 'OWNER')")
    @PatchMapping
    public ResponseEntity deleteReview(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam UUID reviewId) {
        reviewService.deleteReview(userDetails.getUser().getUserId(), reviewId);
        return ResponseEntity.ok(SingleResponse.success("success"));
    }

    //가게 리뷰 평점 불러오기
    @GetMapping("/rating")
    public ResponseEntity findStoreRating(@RequestParam UUID storeId) {
        return ResponseEntity.ok(SingleResponse.success(reviewService.findStoreRating(storeId)));
    }

    // 리뷰평점 계산 강제 호출
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/scheduler")
    public ResponseEntity reviewScheduler() {
        reviewService.forcingScheduler();
        return ResponseEntity.ok(SingleResponse.success("success"));
    }
}
