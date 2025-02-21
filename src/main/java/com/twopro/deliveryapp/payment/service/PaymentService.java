package com.twopro.deliveryapp.payment.service;

import com.twopro.deliveryapp.common.enumType.PaymentProvider;
import com.twopro.deliveryapp.payment.entity.Payment;

import java.util.UUID;

public interface PaymentService {
    Payment createPayment(int totalPrice, PaymentProvider paymentProvider, Long userId);

    Payment findById(UUID paymentId);
}
