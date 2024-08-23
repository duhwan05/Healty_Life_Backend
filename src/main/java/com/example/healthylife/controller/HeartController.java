package com.example.healthylife.controller;

import com.example.healthylife.entity.TodayEntity;
import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.service.HeartService;
import com.example.healthylife.service.TodayService;
import com.example.healthylife.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/hearts")
public class HeartController {

    @Autowired
    private HeartService heartService;

    @Autowired
    private UserService userService;

    @Autowired
    private TodayService todayService;

    @ApiOperation(value = "오늘의 글 좋아요 토글")
    @PostMapping("/toggle/{todaySq}")
    public ResponseEntity<Void> toggleLike(@PathVariable Long todaySq, Authentication authentication) {
        String username = authentication.getName();
        UserEntity user = userService.findUserById(username)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));
        TodayEntity today = todayService.findbytodaysq(todaySq)
                .orElseThrow(() -> new RuntimeException("오늘의 글이 없습니다."));

        heartService.toggleLike(user, today);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "오늘의 글에 대해 좋아요를 눌렀는지 확인")
    @GetMapping("/hasLiked/{todaySq}")
    public ResponseEntity<Boolean> hasUserLiked(@PathVariable Long todaySq) {
        boolean hasLiked = heartService.hasUserLiked(todaySq);
        return ResponseEntity.ok(hasLiked);
    }
}
