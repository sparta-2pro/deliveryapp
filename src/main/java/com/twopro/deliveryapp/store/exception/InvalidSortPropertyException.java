package com.twopro.deliveryapp.store.exception;

public class InvalidSortPropertyException extends RuntimeException {
    public InvalidSortPropertyException(String property) {
        super("정렬이 지원되지 않는 속성입니다: " + property);
    }
}