package com.example.healthylife.controller;

import com.example.healthylife.config.jwt.JwtUtil;
import com.example.healthylife.config.security.MyUserDetails;
import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @ApiOperation(value = "회원 단일 조회")
    @GetMapping("/one")
    public ResponseEntity<UserEntity> findUserById(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = jwtUtil.extractTokenFromHeader(authorizationHeader);

        if (jwtToken == null || !jwtUtil.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String userId = jwtUtil.getUserId(jwtToken);

        Optional<UserEntity> userEntityOptional = userService.findUserById(userId);

        return userEntityOptional
                .map(userEntity -> ResponseEntity.ok(userEntity))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //회원 등록
    @ApiOperation(value = "회원 가입")
    @PostMapping("/signup")
    public ResponseEntity<Object> signUpUser (@RequestBody UserEntity userEntity){
        UserEntity result = userService.signUpUser(userEntity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "회원 수정")
    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody UserEntity userEntity,
                                             @RequestHeader("Authorization") String authorizationHeader) {
        // JWT 토큰 추출
        String jwtToken = jwtUtil.extractTokenFromHeader(authorizationHeader);
        if (jwtToken == null || !jwtUtil.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId = jwtUtil.getUserId(jwtToken);
        userEntity.setUserId(userId);

        // userId로 사용자 조회하여 userSq 설정
        Optional<UserEntity> existingUserOptional = userService.findUserById(userId);
        if (existingUserOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        UserEntity existingUser = existingUserOptional.get();
        userEntity.setUserSq(existingUser.getUserSq()); // userSq를 기존 사용자 정보에서 가져옴

        UserEntity result = userService.updateUser(userEntity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //회원 삭제
    @ApiOperation(value = "회원 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteUser(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = jwtUtil.extractTokenFromHeader(authorizationHeader);

        if (jwtToken == null || !jwtUtil.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long userSq = jwtUtil.getUserSq(jwtToken);
        userService.deleteUserBySq(userSq);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
