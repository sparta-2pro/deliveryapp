package com.twopro.deliveryapp.user.dto;

import com.twopro.deliveryapp.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserResponseDto {
    private Long user_id;
    private String email;
    private String nickname;
    private Date created_at;
    // 비밀번호 제거ㅓ


    public UserResponseDto(Long user_id, String email, String nickname, Date created_at) {
        this.user_id = user_id;
        this.email = email;
        this.nickname = nickname;
        this.created_at = created_at;
    }

    public UserResponseDto(User savedUser) {
        this.user_id = savedUser.getUser_id();
        this.email = savedUser.getEmail();
        this.nickname = savedUser.getNickname();
        this.created_at = savedUser.getCreated_at();
    }
}
