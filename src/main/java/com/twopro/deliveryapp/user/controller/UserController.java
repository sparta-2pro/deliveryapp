package com.twopro.deliveryapp.user.controller;

import com.twopro.deliveryapp.common.dto.SingleResponse;
import com.twopro.deliveryapp.order.excepiton.CustomException;
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
    public ResponseEntity<UserDto> signUp(@RequestBody UserSignupRequestDto requestDto) {
        User user = requestDto.toEntity();
        User signUpUser = userService.signUp(user);

        UserDto responseDto = new UserDto(signUpUser);
        return ResponseEntity.ok(responseDto);
    }

    // 로그인
    @PostMapping("/users/login")
    public ResponseEntity<SingleResponse<String>> login(@RequestBody LoginRequestDto loginRequestDto) {
        String token = userService.login(loginRequestDto);

//        if (token == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(SingleResponse.error("로그인 실패","401"));
//        }
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(SingleResponse.success("로그인 성공"));
    }

    // 로그아웃
    @PostMapping("/auth/logout")
    public ResponseEntity<SingleResponse<String>> logout() {
        return ResponseEntity.ok(SingleResponse.success("로그아웃 성공"));
    }

    // 이메일 중복 확인
    @PostMapping("/users/email-check")
    public ResponseEntity<SingleResponse<String>> checkEmailDuplication(@RequestBody CheckRequestDto requestDto) {
        if (userService.isEmailDuplication(requestDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(SingleResponse.error("이미 가입된 이메일입니다. 다시 확인해주세요!", "409"));
        }
        return ResponseEntity.ok(SingleResponse.success("사용가능한 이메일입니다!"));
    }

    // 닉네임 중복 확인
    @PostMapping("/users/nickname-check")
    public ResponseEntity<SingleResponse<String>> checkNicknameDuplication(@RequestBody CheckRequestDto requestDto) {
        if (userService.isNicknameDuplication(requestDto.getNickname())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(SingleResponse.error("이미 있는 닉네임입니다!", "409"));
        }
        return ResponseEntity.ok(SingleResponse.success("사용가능한 닉네임입니다!"));
    }

    // 회원 정보 조회 - 다른회원 조회 못하게 해야암
    @GetMapping("/auth/{email}")
    public ResponseEntity<SingleResponse<UserResponseDto>> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(SingleResponse.error("회원 정보를 찾을 수 없습니다!", "404"));
        }
        UserResponseDto responseDto = new UserResponseDto(user);
        return ResponseEntity.ok(new SingleResponse<>(responseDto)); // 공통 응답 객체 사용
    }

    // 회원 정보 수정
    @PatchMapping("/auth/{user_id}/update")
    public ResponseEntity<SingleResponse<UserResponseDto>> updateUser(
            @PathVariable Long user_id,
            @RequestBody UserUpdateRequestDto updateDto) {
        UserResponseDto updatedUser = userService.updateUser(user_id, updateDto);
        return ResponseEntity.ok(new SingleResponse<>(updatedUser)); // 공통 응답 객체 사용
    }

    // 회원 탈퇴 비활성화
    @PatchMapping("/auth/{user_id}/delete")
    public ResponseEntity<SingleResponse<String>> deleteUser(@PathVariable Long user_id) {
        userService.deleteUser(user_id);
        return ResponseEntity.ok(new SingleResponse<>("회원 비활성화 성공!")); // 공통 응답 객체 사용
    }


}
