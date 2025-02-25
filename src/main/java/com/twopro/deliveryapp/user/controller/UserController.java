package com.twopro.deliveryapp.user.controller;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.user.dto.*;
import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.exception.CustomException;
import com.twopro.deliveryapp.user.jwt.JwtUtil;
import com.twopro.deliveryapp.user.security.UserDetailsImpl;
import com.twopro.deliveryapp.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    // 회원 가입
    @PostMapping("/users/signup")
    public ResponseEntity<SingleResponse<UserDto>> signUp(@RequestBody @Valid UserSignupRequestDto requestDto) {
        User user = requestDto.toEntity();
        User signUpUser = userService.signUp(user);
        UserDto responseDto = new UserDto(signUpUser);
        log.info("회원가입 요청 받음: {}", requestDto);
        return ResponseEntity.ok(new SingleResponse<>(responseDto));
    }

    // 이메일 중복 확인
    @PostMapping("/users/email-check")
    public ResponseEntity<SingleResponse<String>> checkEmailDuplication(@RequestBody @Valid CheckRequestDto requestDto) {
        if (userService.isEmailDuplication(requestDto.getEmail())) {
            throw new CustomException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다. 다른 이메일을 사용해주세요.");
        }
        return ResponseEntity.ok(new SingleResponse<>("사용가능한 이메일입니다!"));
    }

    // 닉네임 중복 확인
    @PostMapping("/users/nickname-check")
    public ResponseEntity<SingleResponse<String>> checkNicknameDuplication(@RequestBody @Valid CheckRequestDto requestDto) {
        if (userService.isNicknameDuplication(requestDto.getNickname())) {
            throw new CustomException(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다. 다른 닉네임을 사용해주세요.");
        }
        return ResponseEntity.ok(new SingleResponse<>("사용가능한 닉네임입니다!"));
    }

    // 로그인
    @PostMapping("/users/login")
    public ResponseEntity<SingleResponse<String>> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            String token = userService.login(loginRequestDto);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(new SingleResponse<>("로그인 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(new SingleResponse<>(e.getMessage()));
        }
    }

    // 로그아웃
    @PostMapping("/auth/logout")
    public ResponseEntity<SingleResponse<String>> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            String username = userDetails.getUsername();
            // 여기에 토큰 만료 처리 로직 추가 고민 -> 클라이언트에서
            return ResponseEntity.ok(new SingleResponse<>("로그아웃 성공"));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(new SingleResponse<>(e.getMessage()));
        }
    }

    // 내 정보 조회
    @GetMapping("/auth/my-info")
    public ResponseEntity<SingleResponse<UserResponseDto>> getUserByEmail(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String email = userDetails.getUsername();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "해당 사용자가 존재하지 않습니다.");
        }
        if (userDetails.getUser().getDeletedAt() != null) {
            throw new CustomException(HttpStatus.FORBIDDEN, "이미 비활성화된 계정입니다.");
        }

        UserResponseDto responseDto = new UserResponseDto(user);
        return ResponseEntity.ok(new SingleResponse<>(responseDto));
    }

    // 회원 정보 수정
    @PatchMapping("/auth/update")
    public ResponseEntity<SingleResponse<UserResponseDto>> updateUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid UserUpdateRequestDto updateDto) {
        Long userId = userDetails.getUser().getUserId();
        UserResponseDto updatedUser = userService.updateUser(userId, updateDto);
        return ResponseEntity.ok(new SingleResponse<>(updatedUser));
    }

    // 회원 탈퇴 비활성화
    @PatchMapping("/auth/delete")
    public ResponseEntity<SingleResponse<String>> deleteUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails.getUser().getDeletedAt() != null) {
            throw new CustomException(HttpStatus.FORBIDDEN, "이미 비활성화된 계정입니다.");
        }
        Long userId = userDetails.getUser().getUserId();
        userService.deleteUser(userId);
        return ResponseEntity.ok(new SingleResponse<>("회원 비활성화 성공!"));
    }

}
