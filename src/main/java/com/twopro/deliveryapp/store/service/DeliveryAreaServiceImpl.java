package com.twopro.deliveryapp.store.service;

import com.twopro.deliveryapp.store.entity.DeliveryArea;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.entity.StoreDeliveryArea;
import com.twopro.deliveryapp.store.repository.DeliveryAreaRepository;
import com.twopro.deliveryapp.store.repository.StoreDeliveryAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryAreaServiceImpl implements DeliveryAreaService {

    private final DeliveryAreaRepository deliveryAreaRepository;
    private final StoreDeliveryAreaRepository storeDeliveryAreaRepository;

    @Override
    public Optional<DeliveryArea> findByID(UUID id) {
        return deliveryAreaRepository.findById(id);
    }

    @Override
    @Transactional
    public List<DeliveryArea> createDeliveryAreas(List<String> names) {
        List<DeliveryArea> deliveryAreas = names.stream()
                .filter(name -> !deliveryAreaRepository.existsByName(name))
                .map(DeliveryArea::new)
                .toList();

        return deliveryAreaRepository.saveAll(deliveryAreas);
    }

    @Override
    public List<DeliveryArea> getAllDeliveryAreas() {
        return deliveryAreaRepository.findAllByDeletedAtIsNull();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Store> getStoresByDeliveryArea(UUID deliveryAreaId) {
        return storeDeliveryAreaRepository.findByDeliveryAreaIdAndDeletedAtIsNull(deliveryAreaId)
                .stream()
                .map(StoreDeliveryArea::getStore)
                .toList();
    }

    @Override
    @Transactional
    public void updateDeliveryArea(UUID deliveryAreaId, String newName) {
        DeliveryArea deliveryArea = deliveryAreaRepository.findById(deliveryAreaId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 배달 가능 지역을 찾을 수 없습니다."));

        if (deliveryAreaRepository.existsByName(newName)) {
            throw new IllegalStateException("이미 존재하는 배달 가능 지역 이름입니다.");
        }

        deliveryArea.updateName(newName);
        deliveryAreaRepository.save(deliveryArea);
    }

    @Override
    @Transactional
    public void deleteDeliveryArea(UUID deliveryAreaId) {
        DeliveryArea deliveryArea = deliveryAreaRepository.findById(deliveryAreaId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 배달 가능 지역을 찾을 수 없습니다."));

        deliveryArea.delete();
        deliveryAreaRepository.save(deliveryArea);
    }
}