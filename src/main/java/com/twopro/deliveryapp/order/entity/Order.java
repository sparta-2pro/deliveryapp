package com.twopro.deliveryapp.order.entity;

import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.common.entity.BaseEntity;
import com.twopro.deliveryapp.common.enumType.OrderStatus;
import com.twopro.deliveryapp.common.enumType.OrderType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "P_ORDERS")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name="order_id")
    private UUID id;

    @Embedded
    private Address address;

    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @PrePersist
    public void setDefaultOrderStatus() {
        if (this.orderStatus == null) {
            this.orderStatus = OrderStatus.COMPLETED;
        }
    }
}
