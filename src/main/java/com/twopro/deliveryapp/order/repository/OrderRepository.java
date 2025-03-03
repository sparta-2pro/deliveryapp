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
    @Query("select o from Order o join fetch o.store where o.id = :orderId")
    Optional<Order> findByOrderItemsAndMenu(UUID orderId);

    //해당 유저의 모든 주문내용 가져오기
    @Query(value = "select o from Order o join fetch o.store where o.user.userId = :userId",
    countQuery = "select count(o) from Order o where o.user.userId = :userId")
    Page<Order> findAllByUser(Long userId, Pageable pageable);

    @Query("select o from Order o join fetch o.store where o.id =:orderId")
    Optional<Order> findStatusById(UUID orderId);

}
