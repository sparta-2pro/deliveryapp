package com.twopro.deliveryapp.order.excepiton;

import com.twopro.deliveryapp.common.enumType.OrderStatus;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CustomOrderCancelException extends RuntimeException {
    private Long userId;
    private UUID orderId;
    private OrderStatus orderStatus;

    public CustomOrderCancelException(String message, Long userId, UUID orderId, OrderStatus orderStatus) {
        super(message);
        this.userId = userId;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }
}
