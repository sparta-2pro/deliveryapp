package com.twopro.deliveryapp.user.controller;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.user.dto.*;
import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원 가입
    @PostMapping("/users/signup")
    public ResponseEntity<SingleResponse<UserDto>> signUp(@RequestBody UserSignupRequestDto requestDto) {
        User user = requestDto.toEntity();
        User signUpUser = userService.signUp(user);

        UserDto responseDto = new UserDto(signUpUser);
        return ResponseEntity.ok(new SingleResponse<>(responseDto));
    }

    // 로그인
    @PostMapping("/users/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        String token = userService.login(loginRequestDto);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body("로그인 성공");
    }

    // 로그아웃
    @PostMapping("/auth/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("로그아웃 성공");
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
    @GetMapping("/auth/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        UserResponseDto responseDto = new UserResponseDto(user);
        return ResponseEntity.ok(responseDto);
    }

    // 회원 정보 수정
    @PatchMapping("/auth/{user_id}/update")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long user_id,
            @RequestBody UserUpdateRequestDto updateDto) {
        UserResponseDto updatedUser = userService.updateUser(user_id, updateDto);
        return ResponseEntity.ok(updatedUser);
    }

    // 회원 탈퇴 비활성화
    @PatchMapping("/auth/{user_id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long user_id) {
        UserResponseDto deletedUser = userService.deleteUser(user_id);
        return ResponseEntity.ok("회원 비활성화 성공!");
    }


}
