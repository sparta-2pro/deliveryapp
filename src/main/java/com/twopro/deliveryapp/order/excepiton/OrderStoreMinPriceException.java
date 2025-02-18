package com.twopro.deliveryapp.order.excepiton;

public class OrderStoreMinPriceException extends RuntimeException {
    public OrderStoreMinPriceException() {
        super();
    }

    public OrderStoreMinPriceException(String message) {
        super(message);
    }

    public OrderStoreMinPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderStoreMinPriceException(Throwable cause) {
        super(cause);
    }

    protected OrderStoreMinPriceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
