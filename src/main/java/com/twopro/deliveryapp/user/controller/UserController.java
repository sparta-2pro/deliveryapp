package com.twopro.deliveryapp.user.controller;

import com.twopro.deliveryapp.user.dto.LoginRequestDto;
import com.twopro.deliveryapp.user.dto.UserResponseDto;
import com.twopro.deliveryapp.user.dto.UserSignupRequestDto;
import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 가입
    @PostMapping("/users/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserSignupRequestDto requestDto){
        User user = requestDto.toEntity();
        User savedUser = userService.signUp(user);

        UserResponseDto responseDto = new UserResponseDto(savedUser);
        return ResponseEntity.ok(responseDto);
    }

    // 로그인
    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        String result = userService.login(loginRequestDto);
        return ResponseEntity.ok(result);
    }
}
