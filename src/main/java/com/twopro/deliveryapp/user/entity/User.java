package com.twopro.deliveryapp.user.entity;

import com.twopro.deliveryapp.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "p_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String email;
    private String password;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String province;
    private String district;
    private String town;
    private String road_address;
    private String detail_address;


//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Order> orders = new ArrayList<>();
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Store store;
//
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Cart> carts = new ArrayList<>();
//

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Review> reviews = new ArrayList<>();

}
