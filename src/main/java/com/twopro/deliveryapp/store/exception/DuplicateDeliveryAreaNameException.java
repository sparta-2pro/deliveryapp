package com.twopro.deliveryapp.store.exception;

public class DuplicateDeliveryAreaNameException extends RuntimeException {
    private String name;

    public DuplicateDeliveryAreaNameException(String message, String name) {
        super(message);
        this.name = name;
    }
}