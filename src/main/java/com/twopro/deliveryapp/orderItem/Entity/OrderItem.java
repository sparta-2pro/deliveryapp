package com.twopro.deliveryapp.orderItem.Entity;

import com.twopro.deliveryapp.common.entity.BaseEntity;
import com.twopro.deliveryapp.menu.entity.Menu;
import com.twopro.deliveryapp.order.entity.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_orderItem")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "orderItem_id")
    private UUID id;

    private int count; // 주문수량
    private int orderPrice; //주문가격;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="menu_id")
    private Menu menu;

    public static OrderItem createOrderItem(Menu menu, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.menu = menu;
        orderItem.orderPrice = orderPrice;
        orderItem.count = count;
        return orderItem;
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
