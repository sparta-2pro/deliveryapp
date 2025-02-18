package com.twopro.deliveryapp.user.dto;

import com.twopro.deliveryapp.common.dto.AddressDto;
import com.twopro.deliveryapp.common.entity.Address;
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
    private Role role; // 기본값 설정해줘야 하는지?
    private AddressDto address;

    public User toEntity() {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setNickname(this.nickname);
        user.setRole(this.role);

        // User 엔티티에서 Address를 임베딩하는법
        // AddressDto를 Address 객체로 변환하여 Address 엔티티로 설정
        if (this.address != null) {
            Address address = Address.of(this.address);  // AddressDto를 Address 엔티티로 변환
            user.setAddress(address);  // 변환된 Address 객체를 User 엔티티에 설정
        }

        return user;
    }


}
