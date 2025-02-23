package com.twopro.deliveryapp.cart.service;

import com.twopro.deliveryapp.cart.dto.CartMenuDto;
import com.twopro.deliveryapp.cart.entity.Cart;

import java.util.UUID;

public interface CartService {
    Cart getCartByUserId(Long userId);

    void addMenuToCart(UUID cartId, CartMenuDto cartMenuDto);

    void removeMenuFromCart(UUID cartId, UUID menuId);

    Cart getOrCreateCart(Long userId);

    void updateMenuQuantity(UUID cartId, UUID menuId, int quantity);

    void clearCartByUser(Long userId);

    int calculateTotalPrice(Long userId);

}
