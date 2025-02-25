package com.twopro.deliveryapp.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PaymentResponseDto {
   private UUID orderId;
   private int totalPrice;
}
