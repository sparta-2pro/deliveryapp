package com.twopro.deliveryapp.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

// 중복 체크 요청 받을 DTO
@Getter
public class CheckRequestDto {
    @NotNull(message = "이메일은 필수 입력 값입니다.")
    private String email;
    @NotNull(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;
}
