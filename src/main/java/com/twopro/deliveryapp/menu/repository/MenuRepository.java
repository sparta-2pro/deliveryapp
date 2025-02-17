package com.twopro.deliveryapp.menu.repository;

import com.twopro.deliveryapp.menu.entity.MenuEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository {

    MenuEntity addMenu(MenuEntity menuEntity);

    Optional<List<MenuEntity>> findAllMenuByStoreId(String storeId);

    Optional<List<MenuEntity>> findAllMenuByName(String name);
}
