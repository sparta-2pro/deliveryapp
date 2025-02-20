package com.twopro.deliveryapp.order.dto;

import com.twopro.deliveryapp.common.enumType.OrderStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderStatusRequestDto {
    private UUID orderId;
    private OrderStatus orderStatus;
}
