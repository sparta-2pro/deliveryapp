package com.twopro.deliveryapp.store.exception;

public class InvalidSortDirectionException extends RuntimeException {
    public InvalidSortDirectionException(String direction) {
        super("정렬 방향이 유효하지 않습니다: " + direction);
    }
}