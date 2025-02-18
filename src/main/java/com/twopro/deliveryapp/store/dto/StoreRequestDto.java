package com.twopro.deliveryapp.store.dto;

import com.twopro.deliveryapp.common.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequestDto {
    private String name;
    private Long categoryId;
    private AddressDto address;
    private String phone;
    private String operatingHours;
    private String closedDays;
    private String pictureUrl;
    private Integer deliveryType;
    private List<String> deliveryAreas;
    private Integer minimumOrderPrice;
    private Integer deliveryTip;
    private String status;
}
