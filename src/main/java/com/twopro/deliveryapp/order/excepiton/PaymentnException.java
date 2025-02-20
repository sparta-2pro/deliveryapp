package com.twopro.deliveryapp.order.excepiton;

public class PaymentnException extends RuntimeException {
    public PaymentnException() {
        super();
    }

    public PaymentnException(String message) {
        super(message);
    }

    public PaymentnException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentnException(Throwable cause) {
        super(cause);
    }

    protected PaymentnException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
