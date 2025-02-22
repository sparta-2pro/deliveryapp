package com.twopro.deliveryapp.cart.dto;

import com.twopro.deliveryapp.cart.entity.Cart;
import com.twopro.deliveryapp.cart.entity.CartMenu;
import com.twopro.deliveryapp.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartMenuDto {
    private UUID cartMenuId;
    private UUID menuId;
    private int quantity;
    //private int totalPrice;

    public CartMenuDto(CartMenu cartMenu) {
        this.cartMenuId = cartMenu.getCartMenuId();
        this.menuId = cartMenu.getMenu().getMenuId();
        this.quantity = cartMenu.getQuantity();
        //this.totalPrice = cartMenu.getTotalPrice();
    }

    public CartMenu toEntity(Menu menu, Cart cart) {
        CartMenu cartMenu = new CartMenu();
        cartMenu.setCart(cart);
        cartMenu.setMenu(menu);
        cartMenu.setQuantity(this.quantity);
        cartMenu.setTotalPrice(menu.getPrice() * this.quantity);
        return cartMenu;
    }
}
