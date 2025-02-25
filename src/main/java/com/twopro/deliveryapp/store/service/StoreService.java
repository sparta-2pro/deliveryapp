package com.twopro.deliveryapp.store.service;

import com.twopro.deliveryapp.store.dto.StoreRequestDto;
import com.twopro.deliveryapp.store.dto.StoreResponseDto;
import com.twopro.deliveryapp.store.dto.StoreSearchRequestDto;
import com.twopro.deliveryapp.store.entity.Store;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreService {

    Optional<Store> findByID(UUID id);

    Store createStore(StoreRequestDto dto);

    List<StoreResponseDto> getAllStores();

    StoreResponseDto getStoreById(UUID id);

    void updateStore(UUID id, StoreRequestDto dto);

    void deleteStore(UUID id);

    Page<StoreResponseDto> searchStores(StoreSearchRequestDto searchDto);

    List<StoreResponseDto> getStoresByDeliveryAreaAndCategory(UUID deliveryAreaId, UUID categoryId);
}