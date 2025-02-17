package com.twopro.deliveryapp.order.dto;

import lombok.Data;

@Data
public class CreateOrderItemDto {
    private Long itemId;
    private int price;
    private int quantity;
}
