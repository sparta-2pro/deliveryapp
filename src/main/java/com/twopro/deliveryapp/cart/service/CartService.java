package com.twopro.deliveryapp.cart.service;

import com.twopro.deliveryapp.cart.dto.CartMenuDto;
import com.twopro.deliveryapp.cart.entity.Cart;

import java.util.UUID;

public interface CartService {
    Cart getCartByUserId(Long userId);

    Cart addMenuToCart(UUID cartId, CartMenuDto cartMenuDto);

    void removeMenuFromCart(UUID cartId, Long cartMenuId);

    Cart getOrCreatecart(Long userId);
}
