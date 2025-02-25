package com.twopro.deliveryapp.order.excepiton;

import com.twopro.deliveryapp.common.enumType.PaymentProvider;
import lombok.Getter;

@Getter
public class PaymentProviderNoSearchException extends RuntimeException {
    private Long userId;
    private PaymentProvider paymentProvider;

    public PaymentProviderNoSearchException(String message, Long userId, PaymentProvider paymentProvider) {
        super(message);
        this.userId = userId;
        this.paymentProvider = paymentProvider;
    }

    public PaymentProviderNoSearchException(String message) {
        super(message);
    }
}
