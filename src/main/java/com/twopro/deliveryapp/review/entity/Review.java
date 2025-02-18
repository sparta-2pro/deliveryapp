package com.twopro.deliveryapp.review.entity;

import com.twopro.deliveryapp.common.entity.BaseEntity;
import com.twopro.deliveryapp.order.entity.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private UUID id;

    private String contents;
    private int rating;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;
}
