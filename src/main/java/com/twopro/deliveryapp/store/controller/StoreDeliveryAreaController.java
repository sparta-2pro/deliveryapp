package com.twopro.deliveryapp.store.controller;

import com.twopro.deliveryapp.common.dto.MultiResponse;
import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.store.dto.StoreDeliveryAreaDto;
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

    // 가게에 배달 가능 지역 추가
    @PostMapping("/{storeId}")
    public ResponseEntity<SingleResponse<Void>> addStoreDeliveryAreas(@PathVariable UUID storeId, @RequestBody List<UUID> deliveryAreaIds) {
        storeDeliveryAreaService.addStoreDeliveryAreas(storeId, deliveryAreaIds);
        return ResponseEntity.ok(SingleResponse.success(null));
    }

    // 가게의 배달 가능 지역 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<MultiResponse<StoreDeliveryAreaDto>> getDeliveryAreasByStore(@PathVariable UUID storeId) {
        List<StoreDeliveryAreaDto> deliveryAreas = storeDeliveryAreaService.getDeliveryAreasByStore(storeId);
        return ResponseEntity.ok(MultiResponse.success(deliveryAreas));
    }

    // 가게의 배달 지역 수정
    @PutMapping("/{storeId}")
    public ResponseEntity<SingleResponse<Void>> updateDeliveryAreas(@PathVariable UUID storeId, @RequestBody List<UUID> deliveryAreaIds) {
        storeDeliveryAreaService.updateDeliveryAreas(storeId, deliveryAreaIds);
        return ResponseEntity.ok(SingleResponse.success(null));
    }

    // 가게에서 배달 가능 지역 삭제
    @DeleteMapping("/{storeId}/{deliveryAreaId}")
    public ResponseEntity<SingleResponse<Void>> deleteStoreDeliveryArea(@PathVariable UUID storeId, @PathVariable UUID deliveryAreaId) {
        storeDeliveryAreaService.deleteStoreDeliveryArea(storeId, deliveryAreaId);
        return ResponseEntity.ok(SingleResponse.success(null));
    }
}