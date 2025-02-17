package com.twopro.deliveryapp.menu.repository;

import com.twopro.deliveryapp.menu.entity.MenuEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepository {

    private final JpaMenuRepository jpaMenuRepository;

    @Override
    public MenuEntity addMenu(MenuEntity menuEntity) {
        return jpaMenuRepository.save(menuEntity);
    }

    @Override
    public Optional<MenuEntity> findMenuById(String menuId) {
        return jpaMenuRepository.findById(menuId);
    }

    @Override
    public Optional<List<MenuEntity>> findAllMenuByStoreId(String storeId) {
        return jpaMenuRepository.findAllMenuByStoreId(storeId);
    }

    @Override
    public Optional<List<MenuEntity>> findAllMenuByName(String name) {
        return jpaMenuRepository.findMenuEntitiesByName(name);
    }
}
