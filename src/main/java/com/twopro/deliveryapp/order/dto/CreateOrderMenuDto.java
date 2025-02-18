package com.twopro.deliveryapp.order.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateOrderMenuDto {
    private UUID menuId;
    private int menuPrice;
    private int quantity;
}
