package com.twopro.deliveryapp.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "store")
public class Store {

    @Id
    @Column(length = 255, nullable = false)
    private String id;

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

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date updatedAt;

    @Column(length = 255, nullable = true)
    private String deletedAt;

    @Column(length = 255, nullable = true)
    private String createdBy;

    @Column(length = 255, nullable = true)
    private String updatedBy;

    @Column(length = 255, nullable = true)
    private String deletedBy;

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

    @Column(length = 255, nullable = false)
    private String address1;

    @Column(length = 255, nullable = true)
    private String address2;

    @Column(length = 255, nullable = true)
    private String address3;

    @Column(length = 255, nullable = true)
    private String address4;

    @Column(length = 255, nullable = true)
    private String address5;
}