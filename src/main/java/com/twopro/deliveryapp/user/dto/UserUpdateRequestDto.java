package com.twopro.deliveryapp.user.dto;

import com.twopro.deliveryapp.common.dto.AddressDto;
import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.user.entity.Role;
import com.twopro.deliveryapp.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {
    private String email;
    private String password;
    private String nickname;
    private Role role;
    private AddressDto address;

    // User 엔티티로 변환하는 메서드
    public User toEntity() {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setNickname(this.nickname);
        user.setRole(this.role);

        // AddressDto를 Address 엔티티로 변환 후 설정
        if (this.address != null) {
            user.setAddress(Address.of(this.address)); // AddressDto를 Address로 변환하여 설정
        }

        return user;
    }
}
