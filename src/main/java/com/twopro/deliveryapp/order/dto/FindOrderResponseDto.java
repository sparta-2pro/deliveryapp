package com.twopro.deliveryapp.order.dto;

import com.twopro.deliveryapp.common.dto.AddressDto;
import com.twopro.deliveryapp.common.enumType.OrderType;
import com.twopro.deliveryapp.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class FindOrderResponseDto {
    private UUID orderId;
    private AddressDto address;
    private String message;
    private OrderType orderType;
    private int totalPrice;
    private List<OrderMenuResponseDto> menus;
    private UUID storeId;
    private String storeName;
    private int deliveryTip;

    public FindOrderResponseDto(Order o) {
        orderId = o.getId();
        address = AddressDto.of(o.getAddress());
        message = o.getMessage();
        orderType = o.getOrderType();
        totalPrice = o.getTotalPrice();
        deliveryTip = o.getStore().getMinimumOrderPrice();
    }
}
