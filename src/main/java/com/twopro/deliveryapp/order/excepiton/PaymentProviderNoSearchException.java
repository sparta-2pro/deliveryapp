package com.twopro.deliveryapp.order.excepiton;

public class PaymentProviderNoSearchException extends RuntimeException {
    public PaymentProviderNoSearchException() {
        super();
    }

    public PaymentProviderNoSearchException(String message) {
        super(message);
    }

    public PaymentProviderNoSearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentProviderNoSearchException(Throwable cause) {
        super(cause);
    }

    protected PaymentProviderNoSearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
