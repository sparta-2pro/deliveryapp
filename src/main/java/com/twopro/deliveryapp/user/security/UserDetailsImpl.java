package com.twopro.deliveryapp.user.security;

import com.twopro.deliveryapp.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRole() != null
                ? List.of(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().name(); // Role이 enum이라면 그 이름을 권한으로 사용
            }
        })
                : List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // 계정 만료 여부를 체크할 필요가 없다면 true 반환
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // 계정 잠금 여부를 체크할 필요가 없다면 true 반환
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // 자격 증명 만료 여부를 체크할 필요가 없다면 true 반환
    }

    @Override
    public boolean isEnabled() {
        return true;  // 사용자가 활성화되어 있는지 체크할 필요가 없다면 true 반환
    }
}
