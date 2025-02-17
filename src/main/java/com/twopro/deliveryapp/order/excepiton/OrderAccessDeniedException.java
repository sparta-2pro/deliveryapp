package com.twopro.deliveryapp.order.excepiton;

public class OrderAccessDeniedException extends RuntimeException {
    public OrderAccessDeniedException() {
        super();
    }

    public OrderAccessDeniedException(String message) {
        super(message);
    }

    public OrderAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderAccessDeniedException(Throwable cause) {
        super(cause);
    }

    protected OrderAccessDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
