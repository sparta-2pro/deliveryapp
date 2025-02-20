package com.twopro.deliveryapp.store.service;

import com.twopro.deliveryapp.store.entity.DeliveryArea;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.entity.StoreDeliveryArea;
import com.twopro.deliveryapp.store.repository.DeliveryAreaRepository;
import com.twopro.deliveryapp.store.repository.StoreDeliveryAreaRepository;
import com.twopro.deliveryapp.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreDeliveryAreaService {

    private final StoreRepository storeRepository;
    private final DeliveryAreaRepository deliveryAreaRepository;
    private final StoreDeliveryAreaRepository storeDeliveryAreaRepository;

    @Transactional
    public void addStoreDeliveryAreas(UUID storeId, List<UUID> deliveryAreaIds) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 가게를 찾을 수 없습니다."));

        List<StoreDeliveryArea> newDeliveryAreas = deliveryAreaIds.stream()
                .map(areaId -> {
                    DeliveryArea deliveryArea = deliveryAreaRepository.findById(areaId)
                            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 배달 가능 지역을 찾을 수 없습니다."));

                    boolean exists = storeDeliveryAreaRepository.existsByStoreAndDeliveryArea(store, deliveryArea);
                    if (exists) {
                        throw new IllegalStateException("이미 등록된 배달 가능 지역입니다: " + deliveryArea.getName());
                    }

                    return new StoreDeliveryArea(store, deliveryArea);
                })
                .toList();

        storeDeliveryAreaRepository.saveAll(newDeliveryAreas);
    }

    @Transactional(readOnly = true)
    public List<DeliveryArea> getDeliveryAreasByStore(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 가게를 찾을 수 없습니다."));

        return storeDeliveryAreaRepository.findByStore(store).stream()
                .map(StoreDeliveryArea::getDeliveryArea)
                .toList();
    }

    @Transactional
    public void deleteStoreDeliveryArea(UUID storeId, UUID deliveryAreaId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 가게를 찾을 수 없습니다."));
        DeliveryArea deliveryArea = deliveryAreaRepository.findById(deliveryAreaId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 배달 가능 지역을 찾을 수 없습니다."));

        StoreDeliveryArea storeDeliveryArea = storeDeliveryAreaRepository.findByStoreAndDeliveryArea(store, deliveryArea)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게에 등록된 배달 가능 지역이 아닙니다."));

        storeDeliveryAreaRepository.delete(storeDeliveryArea);
    }

    @Transactional
    public void updateDeliveryAreas(UUID storeId, List<UUID> deliveryAreaIds) {
        Store store = storeRepository.findById(storeId).orElseThrow();

        storeDeliveryAreaRepository.deleteAll(storeDeliveryAreaRepository.findByStore(store));

        List<StoreDeliveryArea> newDeliveryAreas = deliveryAreaIds.stream()
                .map(areaId -> new StoreDeliveryArea(store, deliveryAreaRepository.findById(areaId).orElseThrow()))
                .toList();

        storeDeliveryAreaRepository.saveAll(newDeliveryAreas);
    }
}