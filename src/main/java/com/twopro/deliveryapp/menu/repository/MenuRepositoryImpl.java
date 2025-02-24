package com.twopro.deliveryapp.menu.repository;

import com.twopro.deliveryapp.menu.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepository {

    private final JpaMenuRepository jpaMenuRepository;

    @Override
    public Menu addMenu(Menu menu) {
        return jpaMenuRepository.save(menu);
    }

    @Override
    public Optional<Menu> findMenuById(UUID menuId) {
        return jpaMenuRepository.findById(menuId);
    }

    @Override
    public List<Menu> findAllMenuByStoreId(UUID storeId) {
        return jpaMenuRepository.findAllMenuByStoreId(storeId);
    }

    @Override
    public List<Menu> findByMenuIdIn(List<UUID> menuIds) {
        return jpaMenuRepository.findMenusByMenuIdIn(menuIds);
    }

    @Override
    public List<Menu> findAllMenuByName(String name, Long limit) {
        return jpaMenuRepository.findMenuEntitiesByName(name, limit);
    }

    @Override
    public List<Menu> findAllMenuByName(String name, Long limit, UUID lastMenuId) {
        return jpaMenuRepository.findMenuEntitiesByName(name, limit, lastMenuId);
    }
}
