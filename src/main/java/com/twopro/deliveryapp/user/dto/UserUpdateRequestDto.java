package com.twopro.deliveryapp.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String province;
    private String district;
    private String town;
    private String road_address;
    private String detail_address;
    private String role;
}
