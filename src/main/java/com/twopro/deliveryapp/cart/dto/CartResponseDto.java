package com.twopro.deliveryapp.cart.dto;

import com.twopro.deliveryapp.cart.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {
    private UUID cartId;
    private List<CartMenuDto> cartMenus;
    private String message;

    public CartResponseDto(Cart cart) {
        this.cartId = cart.getCartId();
        this.cartMenus = cart.getCartMenus().stream()
                .map(CartMenuDto::new)
                .collect(Collectors.toList());

        if (this.cartMenus.isEmpty()) {
            this.message = "장바구니가 비어있습니다!";
        } else {
            this.message = null; // 장바구니에 아이템이 있을 때는 메시지 없애기
        }
    }
}
