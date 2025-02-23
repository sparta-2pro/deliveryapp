package com.twopro.deliveryapp.store.repository;

import com.twopro.deliveryapp.common.enumType.StoreStatus;
import com.twopro.deliveryapp.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {

    List<Store> findByStatusNot(StoreStatus status);

    Optional<Store> findByStoreIdAndStatusNot(UUID id, StoreStatus status);
}