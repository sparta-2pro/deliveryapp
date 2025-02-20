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

    public CartResponseDto(Cart cart) {
        this.cartId = cart.getCartId();
        this.cartMenus = cart.getCartMenus().stream()
                .map(CartMenuDto::new)
                .collect(Collectors.toList());
    }
}
