package com.twopro.deliveryapp.store.dto;

import com.twopro.deliveryapp.common.dto.AddressDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class StoreResponseDto {
    private UUID id;
    private String name;
    private String pictureUrl;
    private String phone;
    private AddressDto address;
    private String operatingHours;
    private String closedDays;
    private String status;
    private String deliveryType;
    private Integer minimumOrderPrice;
    private Integer deliveryTip;
}