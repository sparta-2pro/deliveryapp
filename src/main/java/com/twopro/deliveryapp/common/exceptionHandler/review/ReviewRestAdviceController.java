package com.twopro.deliveryapp.common.exceptionHandler.review;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.review.exception.ReviewExistException;
import com.twopro.deliveryapp.review.exception.ReviewNotCustomerException;
import com.twopro.deliveryapp.review.exception.ReviewNotFoundException;
import com.twopro.deliveryapp.review.exception.ReviewNotWriteCustomerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ReviewRestAdviceController {
    @ExceptionHandler(ReviewExistException.class)
    public ResponseEntity handleReviewExistException(ReviewExistException e) {
        log.info("errorMessage : {}, userId: {}, orderId : {}", e.getMessage(), e.getUserId(), e.getOrderId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SingleResponse.error(e.getMessage(), "REVIEW_ERROR_1"));
    }

    @ExceptionHandler(ReviewNotCustomerException.class)
    public ResponseEntity handleReviewNotCustomerException(ReviewNotCustomerException e) {
        log.info("errorMessage: {}, userId: {}, orderUserId: {}, orderId: {}", e.getMessage(), e.getUserId(), e.getOrderUserId(), e.getOrderId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SingleResponse.error(e.getMessage(), "REVIEW_ERROR_2"));
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity handleReviewNotFoundException(ReviewNotFoundException e) {
        log.info("errorMessage: {}, userId: {}, reviewId: {}", e.getMessage(), e.getUserId(), e.getReviewId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SingleResponse.error(e.getMessage(), "REVIEW_ERROR_3"));
    }

    @ExceptionHandler(ReviewNotWriteCustomerException.class)
    public ResponseEntity handleReviewNotWriteCustomerException(ReviewNotWriteCustomerException e) {
        log.info("errorMessage: {}, userId: {}, reviewUserId: {}, reviewId: {}", e.getMessage(), e.getUserId(), e.getReviewUserId(), e.getReviewId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SingleResponse.error(e.getMessage(), "REVIEW_ERROR_4"));
    }
}
