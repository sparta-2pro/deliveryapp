package com.twopro.deliveryapp.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequestDto {
    private String name;
    private Long categoryId;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String address5;
    private String phone;
    private String operatingHours;
    private String closedDays;
    private String pictureUrl;
    private Integer deliveryType;
    private String deliveryArea;
    private Integer minimumOrderPrice;
    private Integer deliveryTip;
    private String status;
}
