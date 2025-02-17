package com.twopro.deliveryapp.menu.entity;

import com.twopro.deliveryapp.common.entity.BaseEntity;
import com.twopro.deliveryapp.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "P_MENU")
@NoArgsConstructor
@AllArgsConstructor
public class MenuEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private String menuId;

    @ManyToOne
    @JoinColumn(name = "id")
    private Store store;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private MenuStatus status;

    // TODO s3? test url?
    private String imageUrl;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private int price;
}
