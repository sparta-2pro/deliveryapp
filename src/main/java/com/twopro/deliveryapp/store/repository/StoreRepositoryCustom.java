package com.twopro.deliveryapp.store.repository;

import com.twopro.deliveryapp.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface StoreRepositoryCustom {
    Page<Store> searchStores(UUID categoryId, String storeName, Pageable pageable);
}