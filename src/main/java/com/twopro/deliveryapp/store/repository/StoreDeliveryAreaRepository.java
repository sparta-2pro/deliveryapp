package com.twopro.deliveryapp.store.repository;

import com.twopro.deliveryapp.store.entity.DeliveryArea;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.entity.StoreDeliveryArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StoreDeliveryAreaRepository extends JpaRepository<StoreDeliveryArea, UUID> {

    void deleteAllByStore(Store store);

    boolean existsByStoreAndDeliveryArea(Store store, DeliveryArea deliveryArea);

    List<StoreDeliveryArea> findByDeliveryAreaId(UUID deliveryAreaId);
}