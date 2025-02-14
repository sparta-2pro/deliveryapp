package com.twopro.deliveryapp.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
// WebSecurityConfig 클래스 - 전체 보안 설정
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // 2. AuthenticationManager 빈 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // AuthenticationManager -> 사용자가 로그인할 때 인증을 담당
        // AuthenticationConfiguration -> 매니저를 제공하는 클래스
        return authenticationConfiguration.getAuthenticationManager();
    }



    // 1. SecurityFilterChain 빈 등록
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        // 접근 권한 설정
        http.authorizeHttpRequests((authorizeRequests) ->
                authorizeRequests
                        .requestMatchers("/api/v1/auth/signup","/api/v1/auth/login").permitAll() // 로그인, 회원가입페이지 모두 허용
                        .anyRequest().authenticated()
        );

        return http.build();
    }

}
