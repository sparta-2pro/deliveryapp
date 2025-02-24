package com.twopro.deliveryapp.store.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class StoreSearchRequestDto {
    private UUID categoryId;
    private String storeName;
    private int page = 0;
    private int size = 10;
    private String sortBy;
    private String direction = "asc";
}