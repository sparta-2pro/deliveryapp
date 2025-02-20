package com.twopro.deliveryapp.order.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * 사용자가 주문하러가기 눌렀을 때 쓰는 dto
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private UUID storeId;
    private List<CreateOrderMenuDto> menus;
}
