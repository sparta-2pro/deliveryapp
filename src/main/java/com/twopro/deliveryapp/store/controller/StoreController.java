package com.twopro.deliveryapp.store.controller;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.common.dto.MultiResponse;
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
    public ResponseEntity<SingleResponse<Store>> createStore(@RequestBody StoreRequestDto storeRequestDto) {
        Store store = storeService.createStore(storeRequestDto);
        return ResponseEntity.ok(SingleResponse.success(store));
    }

    // 모든 가게 조회
    @GetMapping
    public ResponseEntity<MultiResponse<Store>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        return ResponseEntity.ok(MultiResponse.success(stores));
    }

    // 해당 가게 조회
    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<Store>> getStoreById(@PathVariable UUID id) {
        return storeService.getStoreById(id)
                .map(store -> ResponseEntity.ok(SingleResponse.success(store)))
                .orElseGet(() -> ResponseEntity.ok(SingleResponse.error("Store not found", "404")));
    }

    // 가게 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<SingleResponse<Void>> updateStore(@PathVariable UUID id, @RequestBody StoreRequestDto storeRequestDto) {
        storeService.updateStore(id, storeRequestDto);
        return ResponseEntity.ok(SingleResponse.success(null));
    }

    // 가게 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<SingleResponse<Void>> deleteStore(@PathVariable UUID id) {
        storeService.deleteStore(id);
        return ResponseEntity.ok(SingleResponse.success(null));
    }
}