package com.twopro.deliveryapp.user.exception;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // 모든 컨트롤러에서 발생한 예외를 처리할 수 있는 전역 예외 처리 클래스
public class GlobalExceptionHandler {

    // CustomException을 처리하는 메서드
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<SingleResponse<String>> handleCustomException(CustomException ex) {
        // 실패 응답 생성
        SingleResponse<String> errorResponse = SingleResponse.error(ex.getMessage(), ex.getHttpStatus().toString());
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    // 다른 예외 처리 추가 가능
    @ExceptionHandler(Exception.class)
    public ResponseEntity<SingleResponse<String>> handleException(Exception ex) {
        // 서버 오류에 대한 실패 응답
        SingleResponse<String> errorResponse = SingleResponse.error("서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<SingleResponse<String>> handleDisabledAccount(DisabledException ex) {
        SingleResponse<String> errorResponse = SingleResponse.error(ex.getMessage(), HttpStatus.FORBIDDEN.toString());
        return new ResponseEntity<>(errorResponse,HttpStatus.FORBIDDEN);
    }
}
