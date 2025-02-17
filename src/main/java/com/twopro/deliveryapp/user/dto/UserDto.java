package com.twopro.deliveryapp.user.dto;

import com.twopro.deliveryapp.user.entity.User;
import lombok.Getter;
import lombok.Setter;

// user 공통 필드 dto
@Getter
@Setter
public class UserDto {
    private Long user_id;
    private String email;
    private String nickname;
    //private Date created_at;

    public UserDto(User user) {
        this.user_id = user.getUser_id();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        //this.created_at = user.getCreated_at();
    }
}
