package com.twopro.deliveryapp.common.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class AddressDto {
    private String zipCode;        // 우편번호 (5자리)
    private String roadAddress;    // 도로명 주소 (ex: "서울특별시 강남구 테헤란로 427")
    private String jibunAddress;   // 지번 주소 (ex: "서울특별시 강남구 삼성동 123-45")
    private String detailAddress;  // 상세 주소 (ex: "101동 1203호")
    private String sido;           // 시/도 (ex: "서울특별시")
    private String sigungu;        // 시/군/구 (ex: "강남구")
    private String eupMyeonDong;   // 읍/면/동 (ex: "삼성동")
    private String buildingName;   // 건물명 (선택 사항, ex: "삼성역 현대타워")
}