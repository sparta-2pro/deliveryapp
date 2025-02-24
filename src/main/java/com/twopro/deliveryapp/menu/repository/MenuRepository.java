package com.twopro.deliveryapp.menu.repository;

import com.twopro.deliveryapp.menu.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuRepository {

    Menu addMenu(Menu menu);

    Optional<Menu> findMenuById(UUID menuId);

    List<Menu> findAllMenuByStoreId(UUID storeId);

    List<Menu> findByMenuIdIn(List<UUID> menuIds);

    List<Menu> findAllMenuByName(String name, Long limit);

    List<Menu> findAllMenuByName(String name, Long limit, UUID lastMenuId);
}
