package com.twopro.deliveryapp.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * 사용자가 주문하러가기 눌렀을 때 쓰는 dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    @NotNull(message = "가게ID는 빈 값일 수 없습니다.")
    private UUID storeId;
    @NotNull(message = "주문을 위한 메뉴의 정보가 없습니다.")
    private List<CreateOrderMenuDto> menus;
}
