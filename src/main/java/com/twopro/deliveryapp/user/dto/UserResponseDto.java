package com.twopro.deliveryapp.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.twopro.deliveryapp.common.dto.AddressDto;
import com.twopro.deliveryapp.user.entity.Role;
import com.twopro.deliveryapp.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto extends UserDto {

    private Role role;
    private AddressDto address;


    // 모든 필드 포함 생성자
    public UserResponseDto(User user) {
        super(user);
        this.role = user.getRole();
        this.address = new AddressDto();
    }

}
