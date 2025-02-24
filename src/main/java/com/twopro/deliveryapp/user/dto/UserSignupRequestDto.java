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
public class UserSignupRequestDto {
    @NotNull(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;
    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 4, max = 16, message = "비밀번호는 4자 이상 16자 이하로 입력해주세요.")
    private String password;
    @NotNull(message = "닉네임은 필수 입력 값입니다.")
    @Size(min = 3, max = 20, message = "닉네임은 3자 이상 20자 이하로 입력해주세요.")
    private String nickname;
    private Role role = Role.CUSTOMER; // 기본값 설정
    @NotNull(message = "주소는 필수 입력 값입니다.")
    private AddressDto address;

    public User toEntity() {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setNickname(this.nickname);
        if (this.role == null) {
            user.setRole(Role.CUSTOMER);  // role이 없으면 기본값으로 설정
        } else {
            user.setRole(this.role);
        }

        // User 엔티티에서 Address를 임베딩하는법
        // AddressDto를 Address 객체로 변환하여 Address 엔티티로 설정
        if (this.address != null) {
            Address address = Address.of(this.address);  // AddressDto를 Address 엔티티로 변환
            user.setAddress(address);  // 변환된 Address 객체를 User 엔티티에 설정
        }

        return user;
    }


}
