package com.twopro.deliveryapp.user.dto;

import com.twopro.deliveryapp.common.dto.AddressDto;
import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.user.entity.Role;
import com.twopro.deliveryapp.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;
    @Size(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하로 입력해주세요.")
    private String password;
    @Size(min = 3, max = 20, message = "닉네임은 3자 이상 20자 이하로 입력해주세요.")
    private String nickname;
    private Role role;
    @NotNull(message = "주소는 필수 입력 값입니다.")
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
