package com.twopro.deliveryapp.order.entity;

import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.common.entity.BaseEntity;
import com.twopro.deliveryapp.common.enumType.OrderStatus;
import com.twopro.deliveryapp.common.enumType.OrderType;
import com.twopro.deliveryapp.orderItem.Entity.OrderItem;
import com.twopro.deliveryapp.review.entity.Review;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

//    @PrePersist
//    public void setDefaultOrderStatus() {
//        if (this.orderStatus == null) {
//            this.orderStatus = OrderStatus.COMPLETED;
//        }
//    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order createOrder(User user, List<OrderItem> orderItems, OrderType orderType, Address address) {
        Order order = new Order();
        order.user = user;
        order.address = address;
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.orderStatus = OrderStatus.COMPLETED;
        order.orderType = orderType;
        return order;
    }


}
