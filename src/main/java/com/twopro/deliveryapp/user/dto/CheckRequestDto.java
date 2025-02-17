package com.twopro.deliveryapp.user.dto;

import lombok.Getter;

// 중복 체크 요청 받을 DTO
@Getter
public class CheckRequestDto {
    private String email;
    private String nickname;
}
