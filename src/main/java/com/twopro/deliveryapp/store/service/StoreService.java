package com.twopro.deliveryapp.store.service;

import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.store.dto.StoreRequestDto;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public Store createStore(StoreRequestDto dto) {
        Store store = Store.builder()
                .categoryId(dto.getCategoryId().toString())
                .name(dto.getName())
                .address(Address.of(dto.getAddress()))
                .phone(dto.getPhone())
                .operatingHours(dto.getOperatingHours())
                .closedDays(dto.getClosedDays())
                .pictureUrl(dto.getPictureUrl())
                .status(dto.getStatus())
                .deliveryType(dto.getDeliveryType().toString())
                .deliveryAreas(dto.getDeliveryAreas())
                .minimumOrderPrice(dto.getMinimumOrderPrice())
                .deliveryTip(dto.getDeliveryTip())
                .build();

        return storeRepository.save(store);
    }

    @Transactional(readOnly = true)
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Store> getStoreById(String id) {
        return storeRepository.findById(UUID.fromString(id));
    }

    @Transactional
    public void updateStore(String id, StoreRequestDto dto) {
        Store store = storeRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NoSuchElementException("가게를 찾을 수 없습니다."));
        store.updateStoreDetails(
                dto.getName(),
                dto.getPhone(),
                dto.getOperatingHours(),
                dto.getClosedDays(),
                dto.getPictureUrl(),
                dto.getCategoryId().toString(),
                dto.getStatus(),
                dto.getDeliveryType().toString(),
                dto.getDeliveryAreas(),
                dto.getMinimumOrderPrice(),
                dto.getDeliveryTip(),
                Address.of(dto.getAddress())
        );
    }

    @Transactional
    public void deleteStore(String id) {
        Store store = storeRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));
        store.delete();
    }
}