package com.twopro.deliveryapp.user.security;

import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.exception.CustomException;
import com.twopro.deliveryapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 사용자 정보 관리
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        System.out.println("로그인 사용자: " + user.getEmail());
        System.out.println("사용자 권한: " + "ROLE_" + user.getRole().name());

        return new UserDetailsImpl(user);
    }
}
