package com.twopro.deliveryapp.order.excepiton;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DeliveryNotAvailableException extends RuntimeException {
    private Long userId;
    private String dong;
    private UUID storeId;

    public DeliveryNotAvailableException(String message, Long userId, String dong, UUID storeId) {
        super(message);
        this.userId = userId;
        this.dong = dong;
        this.storeId = storeId;
    }


}
