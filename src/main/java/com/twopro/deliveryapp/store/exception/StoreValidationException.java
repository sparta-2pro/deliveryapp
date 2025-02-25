package com.twopro.deliveryapp.store.exception;

import lombok.Getter;
import java.util.UUID;

@Getter
public class StoreValidationException extends RuntimeException {
    private String fieldName;
    private String detail;

    public StoreValidationException(String message, String fieldName, String detail) {
        super(message);
        this.fieldName = fieldName;
        this.detail = detail;
    }
}