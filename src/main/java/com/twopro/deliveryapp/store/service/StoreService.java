package com.twopro.deliveryapp.store.service;

import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.store.dto.StoreRequestDto;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public Store createStore(StoreRequestDto dto) {
        Store store = new Store();
        store.setId(dto.getCategoryId().toString());
        store.setName(dto.getName());
        store.setCategoryId(dto.getCategoryId().toString());
        store.setAddress(Address.of(dto.getAddress()));
        store.setPhone(dto.getPhone());
        store.setOperatingHours(dto.getOperatingHours());
        store.setClosedDays(dto.getClosedDays());
        store.setPictureUrl(dto.getPictureUrl());
        store.setStatus(dto.getStatus());
        store.setDeliveryType(dto.getDeliveryType().toString());
        store.setDeliveryArea(dto.getDeliveryArea());
        store.setMinimumOrderPrice(dto.getMinimumOrderPrice());
        store.setDeliveryTip(dto.getDeliveryTip());
        storeRepository.save(store);
        return store;
    }

    @Transactional(readOnly = true)
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Store> getStoreById(String id) {
        return storeRepository.findById(id);
    }

    @Transactional
    public Store updateStore(String id, StoreRequestDto dto) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));
        store.setName(dto.getName());
        store.setCategoryId(dto.getCategoryId().toString());
        store.setAddress(Address.of(dto.getAddress()));
        store.setPhone(dto.getPhone());
        store.setOperatingHours(dto.getOperatingHours());
        store.setClosedDays(dto.getClosedDays());
        store.setPictureUrl(dto.getPictureUrl());
        store.setStatus(dto.getStatus());
        store.setDeliveryType(dto.getDeliveryType().toString());
        store.setDeliveryArea(dto.getDeliveryArea());
        store.setMinimumOrderPrice(dto.getMinimumOrderPrice());
        store.setDeliveryTip(dto.getDeliveryTip());

        return store;
    }

    @Transactional
    public void deleteStore(String id) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));
        store.delete();
    }
}