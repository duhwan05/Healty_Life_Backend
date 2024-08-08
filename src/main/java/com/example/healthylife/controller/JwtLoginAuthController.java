package com.example.healthylife.controller;

import com.example.healthylife.config.jwt.JwtUtil;
import com.example.healthylife.dto.LoginRequest;
import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.service.JwtAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/jwt")
@RestController
@ApiOperation("로그인 컨트롤러")
public class JwtLoginAuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final JwtAuthService jwtAuthService;
    private final ObjectMapper objectMapper;

    @ApiOperation("로그인")
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody LoginRequest loginRequest){
        try {
            // 사용자 이름(아이디)과 비밀번호로 인증
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // 인증 성공하면 JWT 토큰 생성
            String accessToken = jwtUtil.createAccessToken(UserEntity.builder()
                    .userId(authentication.getName())
                    .build());
            String refreshToken = jwtUtil.createRefreshToken(UserEntity.builder()
                    .userId(authentication.getName())
                    .build());
            jwtAuthService.addRefreshToken(refreshToken, loginRequest.getUsername());

            Map<String, String> result = Map.of("access-token", accessToken,
                    "refresh-token", refreshToken);

            return ResponseEntity.ok()
                    .body(objectMapper.writeValueAsString(result));
        } catch (UsernameNotFoundException | BadCredentialsException exception){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("아이디/비밀번호가 맞지 않습니다.");
        } catch (Exception e) {
            log.error("authenticate failed! - username: {}, password: {}", loginRequest.getUsername(), loginRequest.getPassword(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Exception : " + e.getMessage());
        }
    }

// 현재 사용하지 않음
//    @GetMapping("/refresh")
//    public ResponseEntity<String> refresh(@RequestParam("refresh-token") String refreshToken){
//        try {
//            String accessToken = jwtAuthService.refresh(refreshToken);
//            //사용자 이름(아이디)과 비번으로 인증
//
//
//            Map result = Map.of("access-token", accessToken,
//                    "refresh-token", refreshToken);
//
//            //생성된 토큰을 ResponseEntity로 반환
//            return ResponseEntity.ok()
//                    .body(objectMapper.writeValueAsString(result));
//        } catch (Exception e) {
//            log.error("refresh failed! - refresh-token: {}", refreshToken, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Exception : " + e.getMessage());
//        }
//
//
//    }





}