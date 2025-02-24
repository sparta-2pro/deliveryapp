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
            "AND m.deletedAt IS NULL"
    )
    Optional<Menu> findById(@Param("menuId") UUID menuId);

    @Query(
            "SELECT m " +
            "FROM Menu m " +
            "WHERE m.store.storeId = :storeId " +
            "AND m.deletedAt IS NULL"
    )
    List<Menu> findAllMenuByStoreId(@Param("storeId") UUID storeId);

    List<Menu> findMenusByMenuIdIn(Collection<UUID> menuIds);

    // 단순 UUID 로 정렬 중이기 때문에 snowflake 같은 걸로 리팩토링 필요
    @Query(
            value =
                "SELECT * " +
                "FROM P_MENU AS m " +
                "WHERE m.name LIKE %:name% " +
                "AND m.deleted_at IS NULL " +
                "ORDER BY m.menu_id DESC " +
                "LIMIT :limit",
            nativeQuery = true
    )
    List<Menu> findMenuEntitiesByName(@Param("name") String name, @Param("limit") Long limit);

    // 단순 UUID 로 정렬 중이기 때문에 snowflake 같은 걸로 리팩토링 필요
    @Query(
            value =
                    "SELECT * " +
                    "FROM P_MENU AS m" +
                    "WHERE m.name " +
                    "LIKE %:name% " +
                    "AND m.menu_id < :lastMenuId" +
                    "AND m.deleted_at IS NULL " +
                    "ORDER BY m.menu_id DESC " +
                    "LIMIT :limit",
            nativeQuery = true
    )
    List<Menu> findMenuEntitiesByName(
            @Param("name") String name,
            @Param("limit") Long limit,
            @Param("lastMenuId") UUID menuId);
}
