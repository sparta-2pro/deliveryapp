package com.twopro.deliveryapp.user.jwt;

import com.twopro.deliveryapp.user.entity.User;
import com.twopro.deliveryapp.user.repository.UserRepository;
import com.twopro.deliveryapp.user.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final String SECRET_KEY = "afjskjfklsajrljsdifjsalirjaslirsjarsaijklqerfad";
    private final long EXPIRATION_TIME = 3600 * 1000;
    private final UserRepository userRepository;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // JWT 생성
    public String createToken(String email, String role) {
        String token = Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        System.out.println("Generated Token: " + token);
        return token;
    }

    // 헤더에서 JWT 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // JWT 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // 유효성 검증 추가
            System.out.println("JWT validation failed: " + e.getMessage());
            return false;
        }
    }

    // JWT 토큰에서 사용자 이메일 추출
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    // JWT에서 인증 객체 생성 - 추출한 이메일로 DB에서 사용자 정보 찾기
    public Authentication getAuthentication(String token) {
        String email = getUsernameFromToken(token);
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            String role = (String) Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("role");

            System.out.println("Role from token: " + role);

            UserDetails userDetails = new UserDetailsImpl(user.get());

            // 권한을 GrantedAuthority로 변환
            GrantedAuthority authority = new SimpleGrantedAuthority(role);

            // 인증 객체 생성
            return new UsernamePasswordAuthenticationToken(userDetails, null, List.of(authority));
        }
        return null;
    }
}
