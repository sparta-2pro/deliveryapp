package com.twopro.deliveryapp.common.entity;

import com.twopro.deliveryapp.common.dto.AddressDto;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {
    private String roadAddress;    // 도로명 주소 (ex: "서울특별시 강남구 테헤란로 427")
    private String jibunAddress;   // 지번 주소 (ex: "서울특별시 강남구 삼성동 123-45")
    private String detailAddress;  // 상세 주소 (ex: "101동 1203호")
    private String sido;           // 시/도 (ex: "서울특별시")
    private String sigungu;        // 시/군/구 (ex: "강남구")
    private String eupMyeonDong;   // 읍/면/동 (ex: "삼성동")

    public static Address of(AddressDto addressDto) {
        Address address = new Address();
        address.roadAddress = addressDto.getRoadAddress();
        address.jibunAddress = addressDto.getJibunAddress();
        address.detailAddress = addressDto.getDetailAddress();
        address.sido = addressDto.getSido();
        address.sigungu = addressDto.getSigungu();
        address.eupMyeonDong = addressDto.getEupMyeonDong();
        return address;
    }
}
