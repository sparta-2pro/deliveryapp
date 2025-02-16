package com.twopro.deliveryapp.store.repository;

import com.twopro.deliveryapp.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, String> {
}