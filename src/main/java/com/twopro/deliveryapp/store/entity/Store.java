package com.twopro.deliveryapp.store.entity;

import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_store")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(length = 255, nullable = false)
    private UUID StoreId;

    @Column(length = 255, nullable = false)
    private String categoryId;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String pictureUrl;

    @Column(length = 255, nullable = false)
    private String phone;

    @Column(length = 255, nullable = false)
    private String operatingHours;

    @Column(length = 255, nullable = true)
    private String closedDays;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private int reviewCount;

    @Column(length = 255, nullable = false)
    private String status;

    @Column(length = 255, nullable = false)
    private String deliveryType;

    @Column(length = 255, nullable = false)
    private String deliveryArea;

    @Column(nullable = true)
    private Integer minimumOrderPrice;

    @Column(nullable = true)
    private Integer deliveryTip;

    @Embedded
    private Address address;

    @Builder
    public Store(String categoryId, String name, String pictureUrl, String phone, String operatingHours, String closedDays, int rating, int reviewCount, String status, String deliveryType, String deliveryArea, Integer minimumOrderPrice, Integer deliveryTip, Address address) {
        this.categoryId = categoryId;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.phone = phone;
        this.operatingHours = operatingHours;
        this.closedDays = closedDays;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.status = status;
        this.deliveryType = deliveryType;
        this.deliveryArea = deliveryArea;
        this.minimumOrderPrice = minimumOrderPrice;
        this.deliveryTip = deliveryTip;
        this.address = address;
    }

    public void updateStoreDetails(String name, String phone, String operatingHours, String closedDays, String pictureUrl, String categoryId, String status, String deliveryType, String deliveryArea, Integer minimumOrderPrice, Integer deliveryTip, Address address) {
        if (name != null) this.name = name;
        if (phone != null) this.phone = phone;
        if (operatingHours != null) this.operatingHours = operatingHours;
        if (closedDays != null) this.closedDays = closedDays;
        if (pictureUrl != null) this.pictureUrl = pictureUrl;
        if (categoryId != null) this.categoryId = categoryId;
        if (status != null) this.status = status;
        if (deliveryType != null) this.deliveryType = deliveryType;
        if (deliveryArea != null) this.deliveryArea = deliveryArea;
        if (minimumOrderPrice != null) this.minimumOrderPrice = minimumOrderPrice;
        if (deliveryTip != null) this.deliveryTip = deliveryTip;
        if (address != null) this.address = address;
    }
}