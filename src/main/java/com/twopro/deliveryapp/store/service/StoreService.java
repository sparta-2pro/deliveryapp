package com.twopro.deliveryapp.store.service;

import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.common.enumType.OrderType;
import com.twopro.deliveryapp.common.enumType.StoreStatus;
import com.twopro.deliveryapp.store.dto.StoreRequestDto;
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
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final DeliveryAreaRepository deliveryAreaRepository;
    private final StoreDeliveryAreaRepository storeDeliveryAreaRepository;

    @Transactional
    public Store createStore(StoreRequestDto dto) {
        validateStoreStatus(dto.getStatus());
        validateDeliveryType(dto.getDeliveryType());

        Store store = Store.builder()
                .categoryId(dto.getCategoryId().toString())
                .name(dto.getName())
                .address(Address.of(dto.getAddress()))
                .phone(dto.getPhone())
                .operatingHours(dto.getOperatingHours())
                .closedDays(dto.getClosedDays())
                .pictureUrl(dto.getPictureUrl())
                .status(dto.getStatus())
                .deliveryType(dto.getDeliveryType())
                .minimumOrderPrice(dto.getMinimumOrderPrice())
                .deliveryTip(dto.getDeliveryTip())
                .build();

        return store;
    }

    private void validateStoreStatus(StoreStatus status) {
        if (status == null || !(status == StoreStatus.OPEN || status == StoreStatus.CLOSED || status == StoreStatus.DELETED)) {
            throw new IllegalArgumentException("유효하지 않은 상태 값입니다.");
        }
    }

    private void validateDeliveryType(OrderType deliveryType) {
        if (deliveryType == null || !(deliveryType == OrderType.DELIVERY || deliveryType == OrderType.PICKUP)) {
            throw new IllegalArgumentException("유효하지 않은 배달 타입입니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Store> getStoreById(UUID id) {
        return storeRepository.findById(id);
    }

    @Transactional
    public void updateStore(UUID id, StoreRequestDto dto) {
        Store store = storeRepository.findById(id).orElseThrow();
        validateDeliveryType(dto.getDeliveryType());

        store.updateStoreDetails(
                dto.getName(),
                dto.getPhone(),
                dto.getOperatingHours(),
                dto.getClosedDays(),
                dto.getPictureUrl(),
                dto.getCategoryId().toString(),
                dto.getStatus(),
                dto.getDeliveryType(),
                dto.getDeliveryAreas(),
                dto.getMinimumOrderPrice(),
                dto.getDeliveryTip(),
                Address.of(dto.getAddress())
        );
    }

    @Transactional
    public void deleteStore(UUID id) {
        Store store = storeRepository.findById(id).orElseThrow();
        store.delete();
    }

    public List<String> getAvailableDeliveryAreas() {
        return deliveryAreaRepository.findAll().stream()
                .map(DeliveryArea::getName)
                .toList();
    }

    @Transactional
    public void addDeliveryAreaToStore(UUID storeId, UUID deliveryAreaId) {
        Store store = storeRepository.findById(storeId).orElseThrow();
        DeliveryArea deliveryArea = deliveryAreaRepository.findById(deliveryAreaId).orElseThrow();

        boolean exists = storeDeliveryAreaRepository.existsByStoreAndDeliveryArea(store, deliveryArea);
        if (!exists) {
            StoreDeliveryArea sda = new StoreDeliveryArea(store, deliveryArea);
            storeDeliveryAreaRepository.save(sda);
        }
    }

    @Transactional
    public List<Store> getStoresByDeliveryArea(UUID deliveryAreaId) {
        return storeDeliveryAreaRepository.findByDeliveryAreaId(deliveryAreaId).stream()
                .map(StoreDeliveryArea::getStore)
                .toList();
    }

    @Transactional
    public void updateDeliveryAreas(UUID storeId, List<UUID> deliveryAreaIds) {
        Store store = storeRepository.findById(storeId).orElseThrow();

        List<StoreDeliveryArea> newDeliveryAreas = deliveryAreaIds.stream()
                .map(areaId -> new StoreDeliveryArea(store, deliveryAreaRepository.findById(areaId).orElseThrow()))
                .toList();
    }
}