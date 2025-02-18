package com.twopro.deliveryapp.order.entity;

import com.twopro.deliveryapp.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private UUID id;

    private int totalPrice;
    private String paymentStatus;
    private String paymentProvider;
}
