package com.twopro.deliveryapp.store.entity;

import com.twopro.deliveryapp.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_delivery_area")
public class DeliveryArea extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "delivery_area_id")
    private UUID id;

    @Column(nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "deliveryArea")
    private List<StoreDeliveryArea> storeDeliveryAreas;

    public DeliveryArea(String name) {
        this.name = name;
    }

    public void updateName(String newName) {
        this.name = newName;
    }
}