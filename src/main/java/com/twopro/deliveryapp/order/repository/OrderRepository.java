package com.twopro.deliveryapp.order.repository;

import com.twopro.deliveryapp.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
