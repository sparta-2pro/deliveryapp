package com.twopro.deliveryapp.menu.repository;

import com.twopro.deliveryapp.menu.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository {

    Menu addMenu(Menu menu);

    Optional<Menu> findMenuById(String menuId);

    Optional<List<Menu>> findAllMenuByStoreId(String storeId);

    Optional<List<Menu>> findAllMenuByName(String name);
}
