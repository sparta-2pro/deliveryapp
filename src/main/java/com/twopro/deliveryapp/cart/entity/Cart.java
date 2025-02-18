package com.twopro.deliveryapp.cart.entity;

import com.twopro.deliveryapp.menu.entity.MenuEntity;
import com.twopro.deliveryapp.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "p_carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID cart_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void generateId() {
        this.cart_id = UUID.randomUUID();
    }

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartMenu> cartMenus = new ArrayList<>();  // CartMenu 리스트

}
