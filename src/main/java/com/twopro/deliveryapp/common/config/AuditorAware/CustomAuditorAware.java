package com.twopro.deliveryapp.common.config.AuditorAware;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증되지 않은 사용자일 경우 예외를 던짐
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
//            throw new RuntimeException("인증되지 않은 사용자");// security 설정 전이므로 주석처리
            return Optional.of(authentication.getName());
        }

        return Optional.of(authentication.getName());
    }
}
