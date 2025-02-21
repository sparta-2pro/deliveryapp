package com.twopro.deliveryapp.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryAreaDto {
    private UUID id;
    private String name;
}