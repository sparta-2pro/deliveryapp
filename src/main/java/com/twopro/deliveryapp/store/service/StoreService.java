package com.twopro.deliveryapp.store.service;

import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.common.enumType.OrderType;
import com.twopro.deliveryapp.common.enumType.StoreStatus;
import com.twopro.deliveryapp.store.dto.StoreRequestDto;
import com.twopro.deliveryapp.store.entity.Category;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.repository.CategoryRepository;
import com.twopro.deliveryapp.store.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final CategoryRepository categoryRepository;

    public Optional<Store> findByID(UUID id) {
        return storeRepository.findById(id);
    }

    @Transactional
    public Store createStore(StoreRequestDto dto) {
        validateStoreStatus(dto.getStatus());
        validateDeliveryType(dto.getDeliveryType());
        Category category = getCategoryById(dto.getCategoryId());

        Store store = Store.builder()
                .category(category)
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

        return storeRepository.save(store);
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

    private Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 카테고리를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Store getStoreById(UUID id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 가게를 찾을 수 없습니다."));
    }

    @Transactional
    public void updateStore(UUID id, StoreRequestDto dto) {
        Store store = storeRepository.findById(id).orElseThrow();
        validateDeliveryType(dto.getDeliveryType());
        Category category = getCategoryById(dto.getCategoryId());

        store.updateStoreDetails(
                dto.getName(),
                dto.getPhone(),
                dto.getOperatingHours(),
                dto.getClosedDays(),
                dto.getPictureUrl(),
                category,
                dto.getStatus(),
                dto.getDeliveryType(),
                dto.getMinimumOrderPrice(),
                dto.getDeliveryTip(),
                Address.of(dto.getAddress())
        );

        storeRepository.save(store);
    }

    @Transactional
    public void deleteStore(UUID id) {
        Store store = storeRepository.findById(id).orElseThrow();
        store.delete();
        storeRepository.save(store);
    }
}