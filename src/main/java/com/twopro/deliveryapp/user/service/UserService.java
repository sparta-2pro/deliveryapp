package com.twopro.deliveryapp.user.service;

import com.twopro.deliveryapp.user.dto.LoginRequestDto;
import com.twopro.deliveryapp.user.dto.UserResponseDto;
import com.twopro.deliveryapp.user.dto.UserUpdateRequestDto;
import com.twopro.deliveryapp.user.entity.User;


public interface UserService {
    // 회원 가입
    User signUp(User user);

    // 로그인
    String login(LoginRequestDto loginRequestDto);

    // 이메일 중복 검사
    boolean isEmailDuplication(String email);

    // 닉네임 중복 검사
    boolean isNicknameDuplication(String nickname);

    // 내 정보 조회
    User getUserByEmail(String email);

    // 정보 수정
    UserResponseDto updateUser(Long userId, UserUpdateRequestDto updateDto);

    // 회원 탈퇴
    UserResponseDto deleteUser(Long userId);

    User findById(Long userId);
}
