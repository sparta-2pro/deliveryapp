package com.twopro.deliveryapp.user.controller;

import com.twopro.deliveryapp.user.dto.*;
import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<UserDto> signUp(@RequestBody UserSignupRequestDto requestDto) {
        User user = requestDto.toEntity();
        User signUpUser = userService.signUp(user);

        UserDto responseDto = new UserDto(signUpUser);
        return ResponseEntity.ok(responseDto);
    }

    // 로그인
    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        String result = userService.login(loginRequestDto);
        return ResponseEntity.ok(result);
    }

    // 이메일 중복 확인
    @PostMapping("/users/email-check")
    public ResponseEntity<String> checkEmailDuplication(@RequestBody CheckRequestDto requestDto) {
        if (userService.isEmailDuplication(requestDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("이미 가입된 이메일입니다. 다시 확인해주세요!");
        }
        return ResponseEntity.ok().body("사용가능한 이메일입니다!");
    }

    // 닉네임 중복 확인
    @PostMapping("/users/nickname-check")
    public ResponseEntity<String> checkNicknameDuplication(@RequestBody CheckRequestDto requestDto) {
        if (userService.isNicknameDuplication(requestDto.getNickname())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("이미 있는 닉네임입니다!");
        }
        return ResponseEntity.ok().body("사용가능한 닉네임입니다!");
    }

    // 회원 정보 조회
    @GetMapping("/users/{user_id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long user_id) {
        User user = userService.getUserById(user_id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        UserResponseDto responseDto = new UserResponseDto(user);
        return ResponseEntity.ok(responseDto);
    }

    // 회원 정보 수정
    @PatchMapping("/users/{user_id}/update")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long user_id,
            @RequestBody UserUpdateRequestDto updateDto) {
        UserResponseDto updatedUser = userService.updateUser(user_id, updateDto);
        return ResponseEntity.ok(updatedUser);
    }

    // 회원 탈퇴 비활성화
    @PatchMapping("/users/{user_id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long user_id) {
        UserResponseDto deletedUser = userService.deleteUser(user_id);
        return ResponseEntity.ok("회원 비활성화 성공!");
    }
}
