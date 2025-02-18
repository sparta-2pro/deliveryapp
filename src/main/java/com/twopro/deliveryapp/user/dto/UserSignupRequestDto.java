package com.twopro.deliveryapp.user.dto;

import com.twopro.deliveryapp.user.entity.Role;
import com.twopro.deliveryapp.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequestDto {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String nickname;
    private String role;
    private String province;
    private String district;
    private String town;
    private String road_address;
    private String detail_address;

    public User toEntity() {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setNickname(this.nickname);
        user.setRole(Role.valueOf(this.role));
        user.setProvince(this.province);
        user.setDistrict(this.district);
        user.setTown(this.town);
        user.setRoad_address(this.road_address);
        user.setDetail_address(this.detail_address);
        return user;
    }


}
