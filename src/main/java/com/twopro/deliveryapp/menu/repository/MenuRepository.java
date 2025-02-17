package com.twopro.deliveryapp.menu.repository;

import com.twopro.deliveryapp.menu.entity.MenuEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository {

    Optional<List<MenuEntity>> findAllMenuByStoreId(String storeId);
}
