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
import java.util.stream.Collectors;

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
    public ResponseEntity<MultiResponse<StoreResponseDto>> getStoresByDeliveryArea(@PathVariable UUID deliveryAreaId) {
        List<Store> stores = deliveryAreaService.getStoresByDeliveryArea(deliveryAreaId);
        List<StoreResponseDto> storeDtos = stores.stream().map(store -> {
            StoreResponseDto dto = new StoreResponseDto();
            dto.setId(store.getStoreId());
            dto.setName(store.getName());
            dto.setPictureUrl(store.getPictureUrl());
            dto.setPhone(store.getPhone());
            if (store.getAddress() != null) {
                AddressDto addressDto = new AddressDto();
                addressDto.setRoadAddress(store.getAddress().getRoadAddress());
                addressDto.setDetailAddress(store.getAddress().getDetailAddress());
                addressDto.setJibunAddress(store.getAddress().getJibunAddress());
                addressDto.setSido(store.getAddress().getSido());
                addressDto.setSigungu(store.getAddress().getSigungu());
                addressDto.setEupMyeonDong(store.getAddress().getEupMyeonDong());
                dto.setAddress(addressDto);
            }
            dto.setOperatingHours(store.getOperatingHours());
            dto.setClosedDays(store.getClosedDays());
            dto.setStatus(store.getStatus().toString());
            dto.setDeliveryType(store.getDeliveryType());
            dto.setMinimumOrderPrice(store.getMinimumOrderPrice());
            dto.setDeliveryTip(store.getDeliveryTip());
            return dto;
        }).toList();
        return ResponseEntity.ok(MultiResponse.success(storeDtos));
    }
}