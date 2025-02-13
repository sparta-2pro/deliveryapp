package com.twopro.deliveryapp.store.service;

import com.twopro.deliveryapp.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;
}
