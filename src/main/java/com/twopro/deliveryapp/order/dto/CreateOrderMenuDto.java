package com.twopro.deliveryapp.order.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateOrderMenuDto {
    private UUID menuId;
    private int menuPrice;
    private int quantity;
}
