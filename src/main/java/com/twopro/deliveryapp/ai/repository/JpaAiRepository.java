package com.twopro.deliveryapp.ai.repository;

import com.twopro.deliveryapp.ai.entity.Ai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaAiRepository extends JpaRepository<Ai, UUID> {

    @Query(
            value =
                    "SELECT DISTINCT * " +
                            "FROM Ai AS a " +
                            "JOIN FETCH a.menu AS m " +
                            "JOIN FETCH m.store AS s " +
                            "WHERE s.store_id = :storeId " +
                            "ORDER BY a.created_at DESC " +
                            "LIMIT :limit",

            nativeQuery = true
    )
    List<Ai> findAllAiServiceByStoreId(@Param("storeId") UUID storeId, @Param("limit") Long size);


    @Query(
            value =
                    "SELECT DISTINCT a " +
                    "FROM Ai a " +
                    "JOIN FETCH a.menu m " +
                    "JOIN FETCH m.store s " +
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
