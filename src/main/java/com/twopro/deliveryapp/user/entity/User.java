package com.twopro.deliveryapp.user.entity;

import com.twopro.deliveryapp.cart.entity.Cart;
import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.common.entity.BaseEntity;
import com.twopro.deliveryapp.order.entity.Order;
import com.twopro.deliveryapp.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "p_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @Builder
    public User(String email, String password, String nickname, Role role, Address address) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role != null ? role : Role.CUSTOMER; // 기본값 설정
        this.address = address;
    }
}
