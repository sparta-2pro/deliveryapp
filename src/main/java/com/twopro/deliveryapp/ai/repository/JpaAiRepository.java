package com.twopro.deliveryapp.ai.repository;

import com.twopro.deliveryapp.ai.entity.Ai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaAiRepository extends JpaRepository<Ai, UUID> {

    // TODO 쿼리 최적화 필요함
    @Query(
            value =
                "SELECT DISTINCT a.* " +
                "FROM P_Ai a " +
                "LEFT JOIN P_MENU AS m ON a.menu_id = m.menu_id " +
                "LEFT JOIN P_STORE AS s ON m.store_id = s.store_id" +
                "WHERE s.store_id = :storeId " +
                "ORDER BY a.created_at DESC " +
                "LIMIT :limit",
            nativeQuery = true
    )
    List<Ai> findAllAiServiceByStoreId(@Param("storeId") UUID storeId, @Param("limit") Long size);

    // TODO 쿼리 최적화 필요함
    @Query(
            value =
                    "SELECT DISTINCT a.* " +
                    "FROM P_Ai a " +
                    "LEFT JOIN P_MENU AS m ON a.menu_id = m.menu_id " +
                    "LEFT JOIN P_STORE AS s ON m.store_id = s.store_id" +
                    "WHERE s.store_id = :storeId " +
                    "AND a.ai_id < :lastAiId " +
                    "ORDER BY a.created_at DESC " +
                    "LIMIT :limit",
            nativeQuery = true
    )
    List<Ai> findAllAiServiceByStoreId(
            @Param("storeId") UUID storeId,
            @Param("limit") Long size,
            @Param("lastAiId") UUID lastAiId
    );
}
