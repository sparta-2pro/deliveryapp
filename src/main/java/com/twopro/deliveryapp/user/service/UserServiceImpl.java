package com.twopro.deliveryapp.user.service;

import com.twopro.deliveryapp.user.dto.LoginRequestDto;
import com.twopro.deliveryapp.user.dto.UserResponseDto;
import com.twopro.deliveryapp.user.dto.UserUpdateRequestDto;
import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User signUp(User user) {
        return userRepository.save(user);
    }

    @Override
    public String login(LoginRequestDto loginRequestDto) {
        // 로그인 로직 구현
        return "login_token";
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
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
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
            user.setPassword(updateDto.getPassword());
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
            user.setRole(updateDto.getRole());
        }

        user.setUpdated_at(new Date());

        User updatedUser = userRepository.save(user);
        return new UserResponseDto(updatedUser);
    }

    @Override
    public UserResponseDto deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자가 존재하지 않습니다."));
        user.setDeleted_at(new Date());
        user.setUpdated_at(new Date());
        User updatedUser = userRepository.save(user);
        return new UserResponseDto(updatedUser);
    }

    // null, 빈문자열 체크 메소드
    private boolean isValid(String value) {
        return value != null && !value.isEmpty();
    }
}
