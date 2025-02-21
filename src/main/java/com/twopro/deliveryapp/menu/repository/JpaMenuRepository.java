package com.twopro.deliveryapp.menu.repository;

import com.twopro.deliveryapp.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaMenuRepository extends JpaRepository<Menu, UUID> {

    @Query(
            "SELECT m " +
            "FROM Menu m " +
            "WHERE m.menuId = :menuId " +
            "AND m.deleted_at IS NULL"
    )
    Optional<Menu> findById(@Param("menuId") UUID menuId);

    @Query(
            "SELECT m " +
            "FROM Menu m " +
            "WHERE m.store.id = :storeId " +
            "AND m.deleted_at IS NULL"
    )
    Optional<List<Menu>> findAllMenuByStoreId(@Param("storeId") UUID storeId);

    List<Menu> findMenusByMenuIdIn(Collection<UUID> menuIds);

    // 조회시 풀스캔될 위험 있음
    @Query(
            "SELECT m " +
            "FROM Menu m " +
            "WHERE m.name " +
            "LIKE %:name% " +
            "AND m.deleted_at IS NULL "
    )
    Optional<List<Menu>> findMenuEntitiesByName(@Param("name") String name);
}
