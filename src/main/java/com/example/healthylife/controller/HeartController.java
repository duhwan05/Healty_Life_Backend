package com.example.healthylife.controller;

import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.repository.UserRepository;
import com.example.healthylife.service.HeartService;
import com.example.healthylife.service.TodayService;
import com.example.healthylife.service.UserService;
import com.example.healthylife.config.jwt.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hearts")
public class HeartController {

    private final HeartService heartService;
    private final UserService userService;
    private final TodayService todayService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

//    @ApiOperation(value = "오늘의 글 좋아요 토글")
//    @PostMapping("/toggle/{todaySq}")
//    public ResponseEntity<Void> toggleLike(@PathVariable("todaySq") Long todaySq,
//                                           @RequestHeader("Authorization") String authorizationHeader) {
//        String jwtToken = jwtUtil.extractTokenFromHeader(authorizationHeader);
//        if (jwtToken == null || !jwtUtil.validateToken(jwtToken)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        String username = jwtUtil.getUserId(jwtToken);
//        UserEntity user = userService.findUserById(username)
//                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));
//
//        TodayEntity today = todayService.findbytodaysq(todaySq)
//                .orElseThrow(() -> new RuntimeException("오늘의 글이 없습니다."));
//
//        heartService.toggleLike(user, today);
//        return ResponseEntity.ok().build();
//    }

    @ApiOperation(value = "오늘의 글 좋아요 토글")
    @PostMapping("/toggle/{todaySq}")
    public ResponseEntity<?> toggleLike(@PathVariable("todaySq") Long todaySq,
                                           @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = jwtUtil.extractTokenFromHeader(authorizationHeader);
        if (jwtToken == null || !jwtUtil.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = jwtUtil.getUserId(jwtToken);
        UserEntity user = userService.findUserById(username)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));


        long updateCount = heartService.toggleLike(user.getUserSq(), todaySq);
        return ResponseEntity.ok(updateCount);
    }

    @ApiOperation(value = "오늘의 글에 대해 좋아요를 눌렀는지 확인")
    @GetMapping("/hasLiked/{todaySq}")
    public ResponseEntity<Map<String, Boolean>> hasUserLiked(@PathVariable("todaySq") Long todaySq,
                                           @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = jwtUtil.extractTokenFromHeader(authorizationHeader);
        if (jwtToken == null || !jwtUtil.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = jwtUtil.getUserId(jwtToken);
        UserEntity user = userRepository.findByUserId(username)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));

        boolean hasLiked = heartService.hasUserLiked(todaySq, user.getUserSq());

        return ResponseEntity.ok(Collections.singletonMap("hasLiked",hasLiked));
    }
}
