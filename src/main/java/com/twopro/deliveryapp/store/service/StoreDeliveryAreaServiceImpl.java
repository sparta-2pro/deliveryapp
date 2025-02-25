package com.twopro.deliveryapp.store.service;

import com.twopro.deliveryapp.store.dto.StoreDeliveryAreaDto;
import com.twopro.deliveryapp.store.entity.DeliveryArea;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.entity.StoreDeliveryArea;
import com.twopro.deliveryapp.store.exception.DeliveryAreaNotFoundException;
import com.twopro.deliveryapp.store.exception.DuplicateDeliveryAreaException;
import com.twopro.deliveryapp.store.exception.StoreNotFoundException;
import com.twopro.deliveryapp.store.repository.DeliveryAreaRepository;
import com.twopro.deliveryapp.store.repository.StoreDeliveryAreaRepository;
import com.twopro.deliveryapp.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreDeliveryAreaServiceImpl implements StoreDeliveryAreaService {

    private final StoreRepository storeRepository;
    private final DeliveryAreaRepository deliveryAreaRepository;
    private final StoreDeliveryAreaRepository storeDeliveryAreaRepository;

    @Override
    public Optional<StoreDeliveryArea> findByID(UUID id) {
        return storeDeliveryAreaRepository.findById(id);
    }

    @Override
    @Transactional
    public void addStoreDeliveryAreas(UUID storeId, List<UUID> deliveryAreaIds) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException("해당 ID의 가게를 찾을 수 없습니다.", storeId));

        List<StoreDeliveryArea> newDeliveryAreas = deliveryAreaIds.stream()
                .map(areaId -> {
                    DeliveryArea deliveryArea = deliveryAreaRepository.findById(areaId)
                            .orElseThrow(() -> new DeliveryAreaNotFoundException("해당 ID의 배달 가능 지역을 찾을 수 없습니다.", areaId));

                    boolean exists = storeDeliveryAreaRepository.existsByStoreAndDeliveryArea(store, deliveryArea);
                    if (exists) {
                        throw new DuplicateDeliveryAreaException("이미 등록된 배달 가능 지역입니다.", storeId, areaId);
                    }

                    return new StoreDeliveryArea(store, deliveryArea);
                })
                .toList();

        storeDeliveryAreaRepository.saveAll(newDeliveryAreas);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreDeliveryAreaDto> getDeliveryAreasByStore(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException("해당 ID의 가게를 찾을 수 없습니다.", storeId));

        return storeDeliveryAreaRepository.findByStoreAndDeletedAtIsNull(store)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public StoreDeliveryAreaDto convertToDto(StoreDeliveryArea storeDeliveryArea) {
        StoreDeliveryAreaDto dto = new StoreDeliveryAreaDto();
        dto.setId(storeDeliveryArea.getId());
        dto.setStoreName(storeDeliveryArea.getStore().getName());
        dto.setDeliveryAreaName(storeDeliveryArea.getDeliveryArea().getName());
        return dto;
    }

    @Override
    @Transactional
    public void deleteStoreDeliveryArea(UUID storeId, UUID deliveryAreaId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException("해당 ID의 가게를 찾을 수 없습니다.", storeId));
        DeliveryArea deliveryArea = deliveryAreaRepository.findById(deliveryAreaId)
                .orElseThrow(() -> new DeliveryAreaNotFoundException("해당 ID의 배달 가능 지역을 찾을 수 없습니다.", deliveryAreaId));

        StoreDeliveryArea storeDeliveryArea = storeDeliveryAreaRepository.findByStoreAndDeliveryArea(store, deliveryArea)
                .orElseThrow(() -> new DeliveryAreaNotFoundException("해당 가게에 등록된 배달 가능 지역이 아닙니다: ", deliveryAreaId));

        storeDeliveryArea.delete();
        storeDeliveryAreaRepository.save(storeDeliveryArea);
    }

    @Override
    @Transactional
    public void updateStoreDeliveryAreas(UUID storeId, List<StoreDeliveryAreaDto> storeDeliveryAreaDtos) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException("해당 ID의 가게를 찾을 수 없습니다.", storeId));

        storeDeliveryAreaRepository.deleteAll(storeDeliveryAreaRepository.findByStore(store));

        List<StoreDeliveryArea> newAreas = storeDeliveryAreaDtos.stream()
                .map(dto -> {
                    DeliveryArea deliveryArea = deliveryAreaRepository.findById(dto.getId())
                            .orElseThrow(() -> new DeliveryAreaNotFoundException("해당 ID의 배달 가능 지역을 찾을 수 없습니다.", dto.getId()));
                    return new StoreDeliveryArea(store, deliveryArea);
                })
                .collect(Collectors.toList());

        storeDeliveryAreaRepository.saveAll(newAreas);
    }
}