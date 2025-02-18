package com.twopro.deliveryapp.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderMenuResponseDto {
    private String name;
    private int price;
    private int quantity;
}
