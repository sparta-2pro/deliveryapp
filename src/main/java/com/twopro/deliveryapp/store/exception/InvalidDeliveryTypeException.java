package com.twopro.deliveryapp.store.exception;

import lombok.Getter;

@Getter
public class InvalidDeliveryTypeException extends RuntimeException {
    private String detail;

    public InvalidDeliveryTypeException(String message, String detail) {
        super(message);
        this.detail = detail;
    }
}