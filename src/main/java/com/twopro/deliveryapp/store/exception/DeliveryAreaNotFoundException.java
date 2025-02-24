package com.twopro.deliveryapp.store.exception;

import java.util.UUID;

public class DeliveryAreaNotFoundException extends RuntimeException {
    private UUID deliveryAreaId;

    public DeliveryAreaNotFoundException(String message, UUID deliveryAreaId) {
        super(message);
        this.deliveryAreaId = deliveryAreaId;
    }
}