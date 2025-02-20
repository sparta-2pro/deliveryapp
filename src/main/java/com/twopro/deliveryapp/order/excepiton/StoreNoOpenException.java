package com.twopro.deliveryapp.order.excepiton;

public class StoreNoOpenException extends RuntimeException {
    public StoreNoOpenException() {
        super();
    }

    public StoreNoOpenException(String message) {
        super(message);
    }

    public StoreNoOpenException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoreNoOpenException(Throwable cause) {
        super(cause);
    }

    protected StoreNoOpenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
