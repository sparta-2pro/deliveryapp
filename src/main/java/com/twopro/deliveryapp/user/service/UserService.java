package com.twopro.deliveryapp.user.service;

import com.twopro.deliveryapp.user.dto.LoginRequestDto;
import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원 가입
    public User signUp(User user) {
        return userRepository.save(user);
    }

    public String login(LoginRequestDto loginRequestDto) {
        return null;
    }
}
