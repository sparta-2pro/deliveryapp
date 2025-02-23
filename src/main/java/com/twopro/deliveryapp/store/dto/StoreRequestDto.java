package com.twopro.deliveryapp.store.dto;

import com.twopro.deliveryapp.common.dto.AddressDto;
import com.twopro.deliveryapp.common.enumType.OrderType;
import com.twopro.deliveryapp.common.enumType.StoreStatus;
import com.twopro.deliveryapp.common.enumType.StoreType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequestDto {
    private String name;
    private UUID categoryId;
    private AddressDto address;
    private String phone;
    private String operatingHours;
    private String closedDays;
    private String pictureUrl;
    private StoreType deliveryType;
    private List<UUID> deliveryAreas;
    private Integer minimumOrderPrice;
    private Integer deliveryTip;
    private StoreStatus status;
}
