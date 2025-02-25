package com.twopro.deliveryapp.order.excepiton;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;

@Getter
public class OrderAccessDeniedException extends RuntimeException {
    private Long userId;
    private Long orderUserId;

    public OrderAccessDeniedException() {
        super();
    }

    public OrderAccessDeniedException(String message) {
        super(message);
    }

    public OrderAccessDeniedException(String message, Long userId, Long orderUserId) {
        super(message);
        this.userId = userId;
        this.orderUserId = orderUserId;
    }

}
