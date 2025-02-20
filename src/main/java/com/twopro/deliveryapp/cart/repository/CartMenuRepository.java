package com.twopro.deliveryapp.cart.repository;

import com.twopro.deliveryapp.cart.entity.CartMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartMenuRepository extends JpaRepository<CartMenu, UUID> {

    Optional<CartMenu> findByCart_CartIdAndMenu_MenuId(UUID cartId, UUID menuId);

    void deleteAllByCart_CartId(UUID cartId);
}
