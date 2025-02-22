package com.twopro.deliveryapp.cart.entity;

import com.twopro.deliveryapp.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "p_cart_menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartMenu {
    @Id
    @UuidGenerator
    private UUID cartMenuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int quantity;
    private int totalPrice; // 각 메뉴의 총 가격
}
