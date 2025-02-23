package com.twopro.deliveryapp.store.service;

import com.twopro.deliveryapp.store.entity.DeliveryArea;
import com.twopro.deliveryapp.store.entity.StoreDeliveryArea;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreDeliveryAreaService {

    Optional<StoreDeliveryArea> findByID(UUID id);

    void addStoreDeliveryAreas(UUID storeId, List<UUID> deliveryAreaIds);

    List<DeliveryArea> getDeliveryAreasByStore(UUID storeId);

    void deleteStoreDeliveryArea(UUID storeId, UUID deliveryAreaId);

    void updateDeliveryAreas(UUID storeId, List<UUID> deliveryAreaIds);
}