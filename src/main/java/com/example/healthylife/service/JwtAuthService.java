package com.example.healthylife.service;

import com.example.healthylife.config.jwt.JwtUtil;
import com.example.healthylife.entity.UserEntity;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
//토큰들 유효기간 관리하는 곳
public class JwtAuthService {

    private final JwtUtil jwtUtil;

    private Map<String, String> refreshTokenMap = new HashMap<>();
    private Map<String, String> usernameTokenMap = new HashMap<>();

    //생성된 리프레쉬 토큰 저장하는 메소드
    public void addRefreshToken(String refreshToken, String username) {
        refreshTokenMap.put(refreshToken, username);
        usernameTokenMap.put(username, refreshToken);
    }

    //리프래쉬 토큰을 받아서 새로운 acess토큰 생성하는 메소드
    public String refresh(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken) || Objects.isNull(refreshTokenMap.get(refreshToken))) {
            throw new AuthenticationException("유효하지 않은 refresh 토큰입니다!") {};
        }
        Claims claims = jwtUtil.parseClaims(refreshToken);
        //accessToken 생성
        String accessToken = jwtUtil.createAccessToken(UserEntity.builder()
                .userId(claims.get("userId", String.class))
                .build());
        return accessToken;
    }

    //로그아웃 시 두개 토큰 제거하는 메소드
    public void logout(String username) {
        String refreshToken = usernameTokenMap.get(username);
        refreshTokenMap.remove(refreshToken);
        usernameTokenMap.remove(username);
    }
}