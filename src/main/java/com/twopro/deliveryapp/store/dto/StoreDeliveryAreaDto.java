package com.twopro.deliveryapp.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class StoreDeliveryAreaDto {
    private UUID id;
    private String storeName;
    private String deliveryAreaName;
}