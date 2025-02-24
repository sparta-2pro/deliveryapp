package com.twopro.deliveryapp.store.repository;

import com.twopro.deliveryapp.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID>, StoreRepositoryCustom {

    @Query("SELECT s FROM Store s WHERE s.deleted_at IS NULL")
    List<Store> findAllNotDeleted();

    @Query("SELECT s FROM Store s WHERE s.storeId = :id AND s.deleted_at IS NULL")
    Optional<Store> findByStoreIdAndNotDeleted(@Param("id") UUID id);
}