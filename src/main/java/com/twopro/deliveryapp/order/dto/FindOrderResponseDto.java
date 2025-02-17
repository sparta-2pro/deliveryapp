package com.twopro.deliveryapp.order.dto;

import com.twopro.deliveryapp.common.dto.AddressDto;
import com.twopro.deliveryapp.common.enumType.OrderType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class FindOrderResponseDto {
    private UUID orderId;
    private AddressDto address;
    private String message;
    private OrderType orderType;
    private int totalCount;

    private List<OrderMenuResponseDto> menus;
}
