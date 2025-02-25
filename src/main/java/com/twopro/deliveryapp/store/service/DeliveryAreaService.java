package com.twopro.deliveryapp.store.service;

import com.twopro.deliveryapp.store.entity.DeliveryArea;
import com.twopro.deliveryapp.store.entity.Store;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryAreaService {

    Optional<DeliveryArea> findByID(UUID id);

    List<DeliveryArea> createDeliveryAreas(List<String> names);

    List<DeliveryArea> getAllDeliveryAreas();

    void updateDeliveryArea(UUID deliveryAreaId, String newName);

    void deleteDeliveryArea(UUID deliveryAreaId);
}