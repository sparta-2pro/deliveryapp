package com.twopro.deliveryapp.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class DeliveryArea {

    @Id
    @GeneratedValue
    @Column(name = "delivery_area_id")
    private UUID id;

    @Column(nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "deliveryArea")
    private List<StoreDeliveryArea> storeDeliveryAreas;
}