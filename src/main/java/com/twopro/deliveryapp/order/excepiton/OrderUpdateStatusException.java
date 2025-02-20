package com.twopro.deliveryapp.order.excepiton;

import com.twopro.deliveryapp.common.enumType.OrderStatus;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderUpdateStatusException extends RuntimeException {
    private Long userId;
    private UUID orderId;
    private OrderStatus status;

    public OrderUpdateStatusException(String message, Long userId, UUID orderId, OrderStatus status) {
        super(message);
        this.userId = userId;
        this.orderId = orderId;
        this.status = status;
    }

}
