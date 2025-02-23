package com.twopro.deliveryapp.store.repository;

import com.twopro.deliveryapp.store.entity.DeliveryArea;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.entity.StoreDeliveryArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreDeliveryAreaRepository extends JpaRepository<StoreDeliveryArea, UUID> {

    boolean existsByStoreAndDeliveryArea(Store store, DeliveryArea deliveryArea);

    Optional<StoreDeliveryArea> findByStoreAndDeliveryArea(Store store, DeliveryArea deliveryArea);

    List<StoreDeliveryArea> findByStore(Store store);

    @Query("SELECT s FROM StoreDeliveryArea s WHERE s.store = :store AND s.deleted_at IS NULL")
    List<StoreDeliveryArea> findByStoreAndDeletedAtIsNull(Store store);

    @Query("SELECT s FROM StoreDeliveryArea s WHERE s.deliveryArea.id = :deliveryAreaId AND s.deleted_at IS NULL")
    List<StoreDeliveryArea> findByDeliveryAreaIdAndDeletedAtIsNull(UUID deliveryAreaId);
}