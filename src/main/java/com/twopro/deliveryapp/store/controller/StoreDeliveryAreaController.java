package com.twopro.deliveryapp.store.controller;

import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.service.StoreDeliveryAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store-delivery-areas")
public class StoreDeliveryAreaController {

    private final StoreDeliveryAreaService storeDeliveryAreaService;

    // 배달 가능 지역 목록 조회
    @GetMapping
    public ResponseEntity<List<String>> getAvailableDeliveryAreas() {
        List<String> areas = storeDeliveryAreaService.getAvailableDeliveryAreas();
        return ResponseEntity.ok(areas);
    }

    // 특정 배달 가능 지역에서 운영 중인 가게 조회
    @GetMapping("/{deliveryAreaId}/stores")
    public ResponseEntity<List<Store>> getStoreDeliveryArea(@PathVariable UUID deliveryAreaId) {
        List<Store> stores = storeDeliveryAreaService.getStoreDeliveryArea(deliveryAreaId);
        return ResponseEntity.ok(stores);
    }

    // 가게에 배달 가능 지역 추가
    @PostMapping("/{storeId}/{deliveryAreaId}")
    public ResponseEntity<Void> addStoreDeliveryArea(@PathVariable UUID storeId, @PathVariable UUID deliveryAreaId) {
        storeDeliveryAreaService.addStoreDeliveryArea(storeId, deliveryAreaId);
        return ResponseEntity.ok().build();
    }

    // 가게의 배달 지역 수정
    @PutMapping("/{storeId}")
    public ResponseEntity<Void> updateDeliveryAreas(@PathVariable UUID storeId, @RequestBody List<UUID> deliveryAreaIds) {
        storeDeliveryAreaService.updateDeliveryAreas(storeId, deliveryAreaIds);
        return ResponseEntity.ok().build();
    }

    // 가게에서 배달 가능 지역 삭제
    @DeleteMapping("/{storeId}/{deliveryAreaId}")
    public ResponseEntity<Void> deleteStoreDeliveryArea(@PathVariable UUID storeId, @PathVariable UUID deliveryAreaId) {
        storeDeliveryAreaService.deleteStoreDeliveryArea(storeId, deliveryAreaId);
        return ResponseEntity.ok().build();
    }
}