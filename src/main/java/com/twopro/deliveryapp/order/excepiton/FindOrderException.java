package com.twopro.deliveryapp.order.excepiton;

public class FindOrderException extends RuntimeException {

    public FindOrderException() {
        super();
    }

    public FindOrderException(String message) {
        super(message);
    }

    public FindOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public FindOrderException(Throwable cause) {
        super(cause);
    }

    protected FindOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
