package com.twopro.deliveryapp.common.exceptionHandler.all;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class AllRestAdviceController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<SingleResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.badRequest().body(SingleResponse.error(errorMessage, "Validation Error"));
    }
}
