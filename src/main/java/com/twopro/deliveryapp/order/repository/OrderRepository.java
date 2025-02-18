package com.twopro.deliveryapp.order.repository;

import com.twopro.deliveryapp.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("select o from Order o join fetch o.orderItems oi join fetch oi.menu m where oi.order = :order")
    Optional<Order> findByOrderItemsAndMenu(Order order);
}
