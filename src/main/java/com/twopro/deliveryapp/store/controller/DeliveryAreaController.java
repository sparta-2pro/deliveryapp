package com.twopro.deliveryapp.store.controller;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.common.dto.MultiResponse;
import com.twopro.deliveryapp.store.entity.DeliveryArea;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.service.DeliveryAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery-areas")
public class DeliveryAreaController {

    private final DeliveryAreaService deliveryAreaService;

    // 배달 가능 지역 생성
    @PostMapping
    public ResponseEntity<MultiResponse<DeliveryArea>> createDeliveryAreas(@RequestBody List<String> names) {
        List<DeliveryArea> createdAreas = deliveryAreaService.createDeliveryAreas(names);
        return ResponseEntity.ok(MultiResponse.success(createdAreas));
    }

    // 모든 배달 가능 지역 조회
    @GetMapping
    public ResponseEntity<MultiResponse<DeliveryArea>> getAllDeliveryAreas() {
        List<DeliveryArea> areas = deliveryAreaService.getAllDeliveryAreas();
        return ResponseEntity.ok(MultiResponse.success(areas));
    }

    // 배달 가능 지역 수정
    @PutMapping("/{deliveryAreaId}")
    public ResponseEntity<SingleResponse<Void>> updateDeliveryArea(@PathVariable UUID deliveryAreaId, @RequestBody String newName) {
        deliveryAreaService.updateDeliveryArea(deliveryAreaId, newName);
        return ResponseEntity.ok(SingleResponse.success(null));
    }

    // 배달 가능 지역 삭제
    @DeleteMapping("/{deliveryAreaId}")
    public ResponseEntity<SingleResponse<Void>> deleteDeliveryArea(@PathVariable UUID deliveryAreaId) {
        deliveryAreaService.deleteDeliveryArea(deliveryAreaId);
        return ResponseEntity.ok(SingleResponse.success(null));
    }

    // 특정 배달 가능 지역의 가게 조회
    @GetMapping("/{deliveryAreaId}/stores")
    public ResponseEntity<MultiResponse<Store>> getStoresByDeliveryArea(@PathVariable UUID deliveryAreaId) {
        List<Store> stores = deliveryAreaService.getStoresByDeliveryArea(deliveryAreaId);
        return ResponseEntity.ok(MultiResponse.success(stores));
    }
}