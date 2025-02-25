package com.twopro.deliveryapp.store.controller;

import com.twopro.deliveryapp.common.dto.AddressDto;
import com.twopro.deliveryapp.common.dto.MultiResponse;
import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.store.dto.DeliveryAreaDto;
import com.twopro.deliveryapp.store.dto.StoreResponseDto;
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
    public ResponseEntity<MultiResponse<DeliveryAreaDto>> getAllDeliveryAreas() {
        List<DeliveryAreaDto> areas = deliveryAreaService.getAllDeliveryAreas().stream()
                .map(area -> {
                    DeliveryAreaDto dto = new DeliveryAreaDto();
                    dto.setId(area.getId());
                    dto.setName(area.getName());
                    return dto;
                })
                .toList();
        return ResponseEntity.ok(MultiResponse.success(areas));
    }

    // 배달 가능 지역 수정
    @PutMapping()
    public ResponseEntity<SingleResponse<Void>> updateMultipleDeliveryAreas(@RequestBody List<DeliveryAreaDto> deliveryAreaDtos) {
        deliveryAreaService.updateMultipleDeliveryAreas(deliveryAreaDtos);
        return ResponseEntity.ok(SingleResponse.success(null));
    }


    // 배달 가능 지역 삭제
    @DeleteMapping("/{deliveryAreaId}")
    public ResponseEntity<SingleResponse<Void>> deleteDeliveryArea(@PathVariable UUID deliveryAreaId) {
        deliveryAreaService.deleteDeliveryArea(deliveryAreaId);
        return ResponseEntity.ok(SingleResponse.success(null));
    }
}