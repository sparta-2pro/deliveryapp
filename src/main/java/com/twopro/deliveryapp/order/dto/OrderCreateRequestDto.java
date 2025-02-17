package com.twopro.deliveryapp.order.dto;

import com.twopro.deliveryapp.common.dto.AddressDto;
import com.twopro.deliveryapp.common.enumType.OrderType;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreateRequestDto {
    private List<CreateOrderItemDto> items;
    private AddressDto address;
    private String message;
    private OrderType orderType;
}
