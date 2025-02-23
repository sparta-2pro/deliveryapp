package com.twopro.deliveryapp.cart.entity;

import com.twopro.deliveryapp.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

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
    @UuidGenerator
    private UUID cartId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @PrePersist
    public void generateId() {
        if (this.cartId == null) {
            this.cartId = UUID.randomUUID();
        }
    }

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartMenu> cartMenus = new ArrayList<>();  // CartMenu 리스트

    // 메뉴가 이미 장바구니에 있는지 확인하는 메소드
    public CartMenu findExistingCartMenuByMenuId(UUID menuId) {
        return this.cartMenus.stream()
                .filter(cartMenu -> cartMenu.getMenu().getMenuId().equals(menuId))
                .findFirst()
                .orElse(null);  // 없으면 null 반환
    }

}
