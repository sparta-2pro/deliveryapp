package com.twopro.deliveryapp.store.repository;

import com.twopro.deliveryapp.store.entity.DeliveryArea;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.entity.StoreDeliveryArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreDeliveryAreaRepository extends JpaRepository<StoreDeliveryArea, UUID> {

    boolean existsByStoreAndDeliveryArea(Store store, DeliveryArea deliveryArea);

    Optional<StoreDeliveryArea> findByStoreAndDeliveryArea(Store store, DeliveryArea deliveryArea);

    List<StoreDeliveryArea> findByStore(Store store);

    List<StoreDeliveryArea> findByDeliveryAreaId(UUID deliveryAreaId);
}