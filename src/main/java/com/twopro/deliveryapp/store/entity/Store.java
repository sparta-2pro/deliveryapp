package com.twopro.deliveryapp.store.entity;

import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.common.entity.BaseEntity;
import com.twopro.deliveryapp.common.enumType.OrderType;
import com.twopro.deliveryapp.common.enumType.StoreStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_store")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "store_id", length = 255, nullable = false)
    private UUID storeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(length = 255, nullable = false)
    private String name;

    @OneToMany(mappedBy = "store")
    private List<StoreDeliveryArea> storeDeliveryAreas;

    @Column(length = 255, nullable = false)
    private String pictureUrl;

    @Column(length = 255, nullable = false)
    private String phone;

    @Column(length = 255, nullable = false)
    private String operatingHours;

    @Column(length = 255, nullable = true)
    private String closedDays;

    @Column(nullable = false)
    private double rating;

    @Column(nullable = false)
    private int reviewCount;

    @Enumerated(EnumType.STRING)
    @Column(length = 255, nullable = false)
    private StoreStatus status;

    @Enumerated(EnumType.STRING)
    @Column(length = 255, nullable = false)
    private OrderType deliveryType;

    @Column(nullable = true)
    private Integer minimumOrderPrice;

    @Column(nullable = true)
    private Integer deliveryTip;

    @Embedded
    private Address address;

    @Builder
    public Store(Category category, String name, String pictureUrl, String phone, String operatingHours, String closedDays, int rating, int reviewCount, StoreStatus status, OrderType deliveryType, Integer minimumOrderPrice, Integer deliveryTip, Address address) {
        this.category = category;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.phone = phone;
        this.operatingHours = operatingHours;
        this.closedDays = closedDays;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.status = status;
        this.deliveryType = deliveryType;
        this.minimumOrderPrice = minimumOrderPrice;
        this.deliveryTip = deliveryTip;
        this.address = address;
    }

    public void updateStoreDetails(String name, String phone, String operatingHours, String closedDays, String pictureUrl, Category category, StoreStatus status, OrderType deliveryType, Integer minimumOrderPrice, Integer deliveryTip, Address address) {
        if (name != null) this.name = name;
        if (phone != null) this.phone = phone;
        if (operatingHours != null) this.operatingHours = operatingHours;
        if (closedDays != null) this.closedDays = closedDays;
        if (pictureUrl != null) this.pictureUrl = pictureUrl;
        if (category != null) this.category = category;
        if (status != null) this.status = status;
        if (deliveryType != null) this.deliveryType = deliveryType;
        if (minimumOrderPrice != null) this.minimumOrderPrice = minimumOrderPrice;
        if (deliveryTip != null) this.deliveryTip = deliveryTip;
        if (address != null) this.address = address;
    }

    public UUID getCategoryId() {
        return this.category != null ? this.category.getId() : null;
    }

    //가게 리뷰평점, 갯수 변경을 위한 메서드
    public void updateRating(Double rating, int reviewCount) {
        this.rating = rating;
        this.reviewCount = reviewCount;
    }
}