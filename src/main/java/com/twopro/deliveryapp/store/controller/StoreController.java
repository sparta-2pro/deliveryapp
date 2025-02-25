package com.twopro.deliveryapp.store.controller;

import com.twopro.deliveryapp.common.dto.MultiResponse;
import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.store.dto.StoreRequestDto;
import com.twopro.deliveryapp.store.dto.StoreResponseDto;
import com.twopro.deliveryapp.store.dto.StoreSearchRequestDto;
import com.twopro.deliveryapp.store.entity.Store;
import com.twopro.deliveryapp.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<SingleResponse<Store>> createStore(@RequestBody StoreRequestDto storeRequestDto) {
        Store store = storeService.createStore(storeRequestDto);
        return ResponseEntity.ok(SingleResponse.success(store));
    }

    // 모든 가게 조회
    @GetMapping
    public ResponseEntity<MultiResponse<StoreResponseDto>> getAllStores() {
        List<StoreResponseDto> stores = storeService.getAllStores();
        return ResponseEntity.ok(MultiResponse.success(stores));
    }

    // 해당 가게 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<SingleResponse<StoreResponseDto>> getStoreById(@PathVariable UUID storeId) {
        StoreResponseDto store = storeService.getStoreById(storeId);
        return ResponseEntity.ok(SingleResponse.success(store));
    }

    // 가게 정보 수정
    @PutMapping("/{storeId}")
    @PreAuthorize("@storeService.isStoreOwner(#storeId)")
    public ResponseEntity<SingleResponse<Void>> updateStore(
            @PathVariable UUID storeId,
            @RequestBody StoreRequestDto storeRequestDto) {

        storeService.updateStore(storeId, storeRequestDto);
        return ResponseEntity.ok(SingleResponse.success(null));
    }

    // 가게 삭제
    @DeleteMapping("/{storeId}")
    @PreAuthorize("@storeService.isStoreOwner(#storeId)")
    public ResponseEntity<SingleResponse<Void>> deleteStore(@PathVariable UUID storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.ok(SingleResponse.success(null));
    }

    // 가게 검색 및 정렬
    @GetMapping("/search")
    public ResponseEntity<MultiResponse<StoreResponseDto>> searchStores(@ModelAttribute StoreSearchRequestDto searchDto) {
        Page<StoreResponseDto> storePage = storeService.searchStores(searchDto);
        return ResponseEntity.ok(MultiResponse.success(storePage));
    }

    // 카테고리별 특정 배달 가능 지역의 가게 조회
    @GetMapping("/delivery-areas/{deliveryAreaId}/categories/{categoryId}")
    public ResponseEntity<MultiResponse<StoreResponseDto>> getStoresByDeliveryAreaAndCategory(
            @PathVariable UUID deliveryAreaId,
            @PathVariable UUID categoryId) {

        List<StoreResponseDto> stores = storeService.getStoresByDeliveryAreaAndCategory(deliveryAreaId, categoryId);
        return ResponseEntity.ok(MultiResponse.success(stores));
    }
}