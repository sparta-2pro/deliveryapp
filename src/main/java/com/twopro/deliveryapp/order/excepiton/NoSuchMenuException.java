package com.twopro.deliveryapp.order.excepiton;

public class NoSuchMenuException extends RuntimeException{
    public NoSuchMenuException() {
        super();
    }

    public NoSuchMenuException(String message) {
        super(message);
    }

    public NoSuchMenuException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchMenuException(Throwable cause) {
        super(cause);
    }

    protected NoSuchMenuException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
