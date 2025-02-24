package com.twopro.deliveryapp.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    @NotNull(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;
    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
}
