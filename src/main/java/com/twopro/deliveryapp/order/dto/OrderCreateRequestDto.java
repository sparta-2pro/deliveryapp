package com.twopro.deliveryapp.order.dto;

import com.twopro.deliveryapp.common.dto.AddressDto;
import com.twopro.deliveryapp.common.enumType.OrderType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderCreateRequestDto {
    private List<CreateOrderMenuDto> menus;
    private AddressDto address;
    private String message;
    private OrderType orderType;
    private UUID storeId;
}
