package com.twopro.deliveryapp.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.twopro.deliveryapp.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto extends UserDto {
    private String province;
    private String district;
    private String town;
    private String road_address;
    private String detail_address;
    private String role;
    private Date updated_at;


    // 모든 필드 포함 생성자
    public UserResponseDto(User user) {
        super(user);
        this.province = user.getProvince();
        this.district = user.getDistrict();
        this.town = user.getTown();
        this.road_address = user.getRoad_address();
        this.detail_address = user.getDetail_address();
        this.role = String.valueOf(user.getRole());
        this.updated_at = user.getUpdated_at();
    }

}
