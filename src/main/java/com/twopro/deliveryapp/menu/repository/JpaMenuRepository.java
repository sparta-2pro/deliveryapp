package com.twopro.deliveryapp.menu.repository;

import com.twopro.deliveryapp.menu.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaMenuRepository extends JpaRepository<MenuEntity, String> {

    @Query(
            "SELECT m " +
            "FROM MenuEntity m " +
            "WHERE m.store.id = :storeId"
    )
    Optional<List<MenuEntity>> findAllMenuByStoreId(@Param("storeId") String storeId);

    // 조회시 풀스캔될 위험 있음
    @Query(
            "SELECT m " +
            "FROM MenuEntity m " +
            "WHERE m.name " +
            "LIKE %:name%"
    )
    Optional<List<MenuEntity>> findMenuEntitiesByName(@Param("name") String name);
}
