package com.twopro.deliveryapp.common.dto;

import com.twopro.deliveryapp.common.entity.Address;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AddressDto {
    private String zipCode;        // 우편번호 (5자리)
    private String roadAddress;    // 도로명 주소 (ex: "서울특별시 강남구 테헤란로 427")
    private String jibunAddress;   // 지번 주소 (ex: "서울특별시 강남구 삼성동 123-45")
    private String detailAddress;  // 상세 주소 (ex: "101동 1203호")
    private String sido;           // 시/도 (ex: "서울특별시")
    private String sigungu;        // 시/군/구 (ex: "강남구")
    private String eupMyeonDong;   // 읍/면/동 (ex: "삼성동")

    public static AddressDto of(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setZipCode(address.getZipCode());
        addressDto.setRoadAddress(address.getRoadAddress());
        addressDto.setJibunAddress(address.getJibunAddress());
        addressDto.setDetailAddress(address.getDetailAddress());
        addressDto.setSido(address.getSido());
        addressDto.setSigungu(address.getSigungu());
        addressDto.setEupMyeonDong(address.getEupMyeonDong());
        return addressDto;
    }
}