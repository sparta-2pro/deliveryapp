package com.twopro.deliveryapp.store.repository;

import com.twopro.deliveryapp.store.entity.DeliveryArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DeliveryAreaRepository extends JpaRepository<DeliveryArea, UUID> {

    boolean existsByName(String name);

    @Query("SELECT d FROM DeliveryArea d WHERE d.deletedAt IS NULL")
    List<DeliveryArea> findAllByDeletedAtIsNull();
}