package com.twopro.deliveryapp.store.service;

import com.twopro.deliveryapp.store.dto.DeliveryAreaDto;
import com.twopro.deliveryapp.store.entity.DeliveryArea;
import com.twopro.deliveryapp.store.entity.Store;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryAreaService {

    Optional<DeliveryArea> findByID(UUID id);

    List<DeliveryArea> createDeliveryAreas(List<String> names);

    List<DeliveryArea> getAllDeliveryAreas();

    void updateDeliveryArea(DeliveryAreaDto deliveryAreaDto);

    void deleteDeliveryArea(UUID deliveryAreaId);
}