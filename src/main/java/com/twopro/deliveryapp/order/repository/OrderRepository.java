package com.twopro.deliveryapp.order.repository;

import com.twopro.deliveryapp.order.entity.Order;
import com.twopro.deliveryapp.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    //오더id로 주문 단건 조회
    @Query("select o from Order o join fetch o.orderItems oi join fetch oi.menu m where oi.order.id = :orderId")
    Optional<Order> findByOrderItemsAndMenu(UUID orderId);

    //해당 유저의 모든 주문내용 가져오기
    @Query("select o from Order o join fetch o.orderItems oi join fetch oi.menu where o.user = :user")
    Page<Order> findAllByUser(User user, Pageable pageable);
}
