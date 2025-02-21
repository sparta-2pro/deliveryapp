package com.twopro.deliveryapp.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_store_delivery_area")
public class StoreDeliveryArea {
    @Id
    @GeneratedValue
    @Column(name = "store_delivery_area_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_area_id")
    private DeliveryArea deliveryArea;

    public StoreDeliveryArea(Store store, DeliveryArea deliveryArea) {
        this.store = store;
        this.deliveryArea = deliveryArea;
    }
}