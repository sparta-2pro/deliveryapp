package com.twopro.deliveryapp.user.exception;

import org.springframework.http.HttpStatus;

// 예외 메시지. HTTP 상태코드 처리

public class CustomException extends RuntimeException {
    private final HttpStatus httpStatus;

    public CustomException(HttpStatus httpStatus, String message ) {
        super(message); // 부모 클래스의 생성자를 호출하여 메시지 저장
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
