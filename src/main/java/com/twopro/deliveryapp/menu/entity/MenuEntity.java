package com.twopro.deliveryapp.menu.entity;

import com.twopro.deliveryapp.common.entity.BaseEntity;
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

//    @ManyToOne
//    @JoinColumn(name = "storeId")
//    private Store store;

    // TODO s3? test url?
    private String imageUrl;

    private MenuStatus status;

    private String description;

    private int price;
}
