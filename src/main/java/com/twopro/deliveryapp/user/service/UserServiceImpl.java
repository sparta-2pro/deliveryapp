package com.twopro.deliveryapp.user.service;

import com.twopro.deliveryapp.user.dto.LoginRequestDto;
import com.twopro.deliveryapp.user.dto.UserResponseDto;
import com.twopro.deliveryapp.user.dto.UserUpdateRequestDto;
import com.twopro.deliveryapp.user.entity.Role;
import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.jwt.JwtUtil;
import com.twopro.deliveryapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public String login(LoginRequestDto loginRequestDto) {
        // 로그인 로직 구현
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));

        if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            String role = "ROLE_" + user.getRole().name();
            return jwtUtil.createToken(user.getEmail(), role);
        } else {
            throw new IllegalArgumentException("Invalid password");
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
                .orElseThrow(() -> new RuntimeException("해당 사용자가 존재하지 않습니다."));
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserUpdateRequestDto updateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자가 존재하지 않습니다."));

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
        if (isValid(updateDto.getProvince())) {
            user.setProvince(updateDto.getProvince());
        }
        if (isValid(updateDto.getDistrict())) {
            user.setDistrict(updateDto.getDistrict());
        }
        if (isValid(updateDto.getTown())) {
            user.setTown(updateDto.getTown());
        }
        if (isValid(updateDto.getRoad_address())) {
            user.setRoad_address(updateDto.getRoad_address());
        }
        if (isValid(updateDto.getDetail_address())) {
            user.setDetail_address(updateDto.getDetail_address());
        }
        if (isValid(updateDto.getRole())) {
            user.setRole(Role.valueOf(updateDto.getRole()));
        }

        User updatedUser = userRepository.save(user);
        return new UserResponseDto(updatedUser);
    }

    @Override
    public UserResponseDto deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자가 존재하지 않습니다."));

        user.delete(); // BaseEntity의 delete() 메서드 호출

        User updatedUser = userRepository.save(user);
        return new UserResponseDto(updatedUser);
    }

    // null, 빈문자열 체크 메소드
    private boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }
}
