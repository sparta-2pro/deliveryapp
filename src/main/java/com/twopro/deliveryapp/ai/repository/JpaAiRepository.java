package com.twopro.deliveryapp.ai.repository;

import com.twopro.deliveryapp.ai.entity.Ai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaAiRepository extends JpaRepository<Ai, UUID> {

    @Query(
            "SELECT DISTINCT a " +
            "FROM Ai a " +
            "JOIN FETCH a.menu m " +
            "JOIN FETCH m.store s " +
            "WHERE s.storeId = :storeId " +
            "ORDER BY a.createdAt DESC"
    )
    List<Ai> findAllAiServiceByStoreId(@Param("storeId") UUID storeId);
}
