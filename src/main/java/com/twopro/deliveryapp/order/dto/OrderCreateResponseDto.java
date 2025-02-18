package com.twopro.deliveryapp.order.dto;

import com.twopro.deliveryapp.common.dto.AddressDto;
import com.twopro.deliveryapp.common.enumType.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderCreateResponseDto {
    private UUID orderId;
    private AddressDto address;
    private OrderType orderType;
    private int totalPrice;
    private List<OrderMenuResponseDto> menus;


    public OrderCreateResponseDto(UUID orderId, AddressDto address, OrderType orderType, int totalPrice, List<OrderMenuResponseDto> menus) {
        this.orderId = orderId;
        this.address = address;
        this.orderType = orderType;
        this.totalPrice = menus.stream().mapToInt(m->m.getPrice() * m.getQuantity()).sum();;
        this.menus = menus;
    }
}
