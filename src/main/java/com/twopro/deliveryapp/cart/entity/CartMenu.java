package com.twopro.deliveryapp.cart.entity;

import com.twopro.deliveryapp.menu.entity.MenuEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "p_cart_menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private MenuEntity menu;

    private int quantity;
    private int totalPrice;
}
