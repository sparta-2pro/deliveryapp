package com.twopro.deliveryapp.store.exception;

import lombok.Getter;
import java.util.UUID;

@Getter
public class StoreNotFoundException extends RuntimeException {
    private UUID storeId;

    public StoreNotFoundException(String message, UUID storeId) {
        super(message);
        this.storeId = storeId;
    }
}