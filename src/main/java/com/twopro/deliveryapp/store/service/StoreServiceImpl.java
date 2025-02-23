package com.twopro.deliveryapp.store.service;

import com.twopro.deliveryapp.common.dto.AddressDto;
import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.common.enumType.StoreStatus;
import com.twopro.deliveryapp.common.enumType.StoreType;
import com.twopro.deliveryapp.store.dto.StoreRequestDto;
import com.twopro.deliveryapp.store.dto.StoreResponseDto;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Optional<Store> findByID(UUID id) {
        return storeRepository.findById(id);
    }

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public List<StoreResponseDto> getAllStores() {
        List<Store> stores = storeRepository.findByStatusNot(StoreStatus.DELETED);
        return stores.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StoreResponseDto getStoreById(UUID id) {
        Store store = storeRepository.findByStoreIdAndStatusNot(id, StoreStatus.DELETED)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 가게를 찾을 수 없거나 삭제되었습니다."));
        return convertToDto(store);
    }

    private StoreResponseDto convertToDto(Store store) {
        StoreResponseDto dto = new StoreResponseDto();
        dto.setId(store.getStoreId());
        dto.setName(store.getName());
        dto.setPictureUrl(store.getPictureUrl());
        dto.setPhone(store.getPhone());
        dto.setAddress(AddressDto.of(store.getAddress()));
        dto.setOperatingHours(store.getOperatingHours());
        dto.setClosedDays(store.getClosedDays());
        dto.setStatus(store.getStatus().name());
        dto.setDeliveryType(store.getDeliveryType());
        dto.setMinimumOrderPrice(store.getMinimumOrderPrice());
        dto.setDeliveryTip(store.getDeliveryTip());
        return dto;
    }

    @Override
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

    @Override
    @Transactional
    public void deleteStore(UUID id) {
        Store store = storeRepository.findById(id).orElseThrow();
        store.deleteStore();
        store.delete();
        storeRepository.save(store);
    }

    private void validateStoreStatus(StoreStatus status) {
        if (status == null || !(status == StoreStatus.OPEN || status == StoreStatus.CLOSED || status == StoreStatus.DELETED)) {
            throw new IllegalArgumentException("유효하지 않은 상태 값입니다.");
        }
    }

    private void validateDeliveryType(StoreType deliveryType) {
        if (deliveryType == null || !(deliveryType == StoreType.DELIVERY || deliveryType == StoreType.PICKUP || deliveryType == StoreType.DELIVERY_AND_PICKUP)) {
            throw new IllegalArgumentException("유효하지 않은 배달 타입입니다.");
        }
    }

    private Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 카테고리를 찾을 수 없습니다."));
    }
}