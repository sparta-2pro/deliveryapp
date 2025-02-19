package com.twopro.deliveryapp.store.controller;

import com.twopro.deliveryapp.store.dto.StoreRequestDto;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    // 가게 생성
    @PostMapping
    public ResponseEntity<Store> createStore(@RequestBody StoreRequestDto storeRequestDto) {
        Store store = storeService.createStore(storeRequestDto);
        return ResponseEntity.ok(store);
    }

    // 모든 가게 조회
    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        return ResponseEntity.ok(stores);
    }

    // 해당 가게 조회
    @GetMapping("/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable UUID id) {
        return storeService.getStoreById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 가게 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStore(@PathVariable UUID id, @RequestBody StoreRequestDto storeRequestDto) {
        storeService.updateStore(id, storeRequestDto);
        return ResponseEntity.noContent().build();
    }

    // 가게 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable UUID id) {
        storeService.deleteStore(id);
        return ResponseEntity.ok().build();
    }

    // 등록된 배달 가능 지역 목록 조회
    @GetMapping("/delivery-areas")
    public ResponseEntity<List<String>> getAvailableDeliveryAreas() {
        List<String> areas = storeService.getAvailableDeliveryAreas();
        return ResponseEntity.ok(areas);
    }

    // 해당 배달 지역의 가게 조회
    @GetMapping("/delivery-areas/{deliveryAreaId}")
    public ResponseEntity<List<Store>> getStoresByDeliveryArea(@PathVariable UUID deliveryAreaId) {
        List<Store> stores = storeService.getStoresByDeliveryArea(deliveryAreaId);
        return ResponseEntity.ok(stores);
    }

    // 해당 가게의 배달 지역 추가
    @PostMapping("/{storeId}/delivery-areas/{deliveryAreaId}")
    public ResponseEntity<Void> addDeliveryAreaToStore(@PathVariable UUID storeId, @PathVariable UUID deliveryAreaId) {
        storeService.addDeliveryAreaToStore(storeId, deliveryAreaId);
        return ResponseEntity.ok().build();
    }

    // 해당 가게의 배달 지역 수정
    @PutMapping("/{storeId}/delivery-areas")
    public ResponseEntity<Void> updateDeliveryAreas(@PathVariable UUID storeId, @RequestBody List<UUID> deliveryAreaIds) {
        storeService.updateDeliveryAreas(storeId, deliveryAreaIds);
        return ResponseEntity.ok().build();
    }
}