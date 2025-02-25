package com.twopro.deliveryapp.store.exception;

import java.util.UUID;

public class DuplicateDeliveryAreaException extends RuntimeException {
    private UUID storeId;
    private UUID deliveryAreaId;

    public DuplicateDeliveryAreaException(String message, UUID storeId, UUID deliveryAreaId) {
        super(message);
        this.storeId = storeId;
        this.deliveryAreaId = deliveryAreaId;
    }
}