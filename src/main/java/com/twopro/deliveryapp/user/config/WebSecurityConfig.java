package com.twopro.deliveryapp.user.config;

import com.twopro.deliveryapp.user.jwt.JwtAuthenticationFilter;
import com.twopro.deliveryapp.user.jwt.JwtAuthorizationFilter;
import com.twopro.deliveryapp.user.jwt.JwtUtil;
import com.twopro.deliveryapp.user.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// WebSecurityConfig 클래스 - 전체 보안 설정
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

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
                        // 인증 없이 접근 가능 경로 (회원가입, 로그인)
                        .requestMatchers("/api/v1/users/**").permitAll()
                        // 권한을 가진 사용자만 접근 가능 경로
                        .requestMatchers("/api/v1/auth/**").hasAnyRole("CUSTOMER", "OWNER", "ADMIN")
                        .requestMatchers("/api/v1/carts/**", "/api/v1/orders/**", "/api/v1/reviews/**").hasAnyRole("CUSTOMER", "OWNER", "ADMIN")
                        .requestMatchers("/api/v1/stores/**").hasAnyRole("OWNER","ADMIN")
                        .requestMatchers("/api/v1/ai/**", "/api/v1/stores/**","/api/v1/orders/{orderId}/status").hasRole("OWNER")
                        // ADMIN 권한을 가진 사용자만 접근 가능한 경로
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated());
                        //.anyRequest().permitAll());

        // JWT 인증 필터 추가
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);

        return http.build();
    }

    // jwt 인증 필터 빈 등록
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(jwtUtil);

    }

    // jwt 인가 필터 빈 등록
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

}
