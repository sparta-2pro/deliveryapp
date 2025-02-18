package com.twopro.deliveryapp.user.service;

import com.twopro.deliveryapp.common.entity.Address;
import com.twopro.deliveryapp.user.dto.LoginRequestDto;
import com.twopro.deliveryapp.user.dto.UserResponseDto;
import com.twopro.deliveryapp.user.dto.UserUpdateRequestDto;
import com.twopro.deliveryapp.user.entity.Role;
import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.exception.CustomException;
import com.twopro.deliveryapp.user.jwt.JwtUtil;
import com.twopro.deliveryapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signUp(User user) {
        if (isEmailDuplication(user.getEmail())) {
            throw new CustomException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다. 다른 이메일을 사용해주세요.");
        }

        if (isNicknameDuplication(user.getNickname())) {
            throw new CustomException(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다. 다른 닉네임을 사용해주세요.");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public String login(LoginRequestDto loginRequestDto) {
        // 로그인 로직 구현
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호가 틀렸습니다."));

        // 비밀번호 체크
        if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            String role = "ROLE_" + user.getRole().name();
            return jwtUtil.createToken(user.getEmail(), role);
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다.");
        }
    }

    @Override
    public boolean isEmailDuplication(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isNicknameDuplication(String nickname) {
        return userRepository.existsByNickname(nickname);
    }


    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 사용자가 존재하지 않습니다."));
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserUpdateRequestDto updateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 사용자가 존재하지 않습니다."));

        // 정보 수정
        if (isValid(updateDto.getEmail())) {
            user.setEmail(updateDto.getEmail());
        }
        if (isValid(updateDto.getPassword())) {
            user.setPassword(passwordEncoder.encode(updateDto.getPassword()));
        }
        if (isValid(updateDto.getNickname())) {
            user.setNickname(updateDto.getNickname());
        }
        // AddressDto를 Address로 변환 후 업데이트
        if (updateDto.getAddress() != null) {
            user.setAddress(Address.of(updateDto.getAddress()));
        }
        if (isValid(String.valueOf(updateDto.getRole()))) {
            user.setRole(Role.valueOf(String.valueOf(updateDto.getRole())));
        }

        User updatedUser = userRepository.save(user);
        return new UserResponseDto(updatedUser);
    }

    @Override
    public UserResponseDto deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 사용자가 존재하지 않습니다."));

        user.delete(); // BaseEntity의 delete() 메서드 호출

        User updatedUser = userRepository.save(user);
        return new UserResponseDto(updatedUser);
    }

    // null, 빈문자열 체크 메소드
    private boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }
}
