package com.example.healthylife.controller;

import com.example.healthylife.config.jwt.JwtUtil;
import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.entity.TodayEntity;
import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.repository.UserRepository;
import com.example.healthylife.service.TodayService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/today")
@RestController
@RequiredArgsConstructor
public class TodayController {
    private final TodayService todayService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @ApiOperation(value = "오운완 전체조회")
    @GetMapping("/all")
    public List<TodayEntity> todayList(){
        List<TodayEntity> todayEntities = todayService.todayList();
        return todayEntities;
    }

    @ApiOperation(value = "오운완 내가쓴 글 조회")
    @GetMapping("/myTodayContents")
    public ResponseEntity<List<TodayEntity>> myTodayContents(@RequestHeader("Authorization") String authorizationHeader){
        String jwtToken = jwtUtil.extractTokenFromHeader(authorizationHeader);
        if (jwtToken == null || !jwtUtil.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId = jwtUtil.getUserId(jwtToken);
        List<TodayEntity> todayEntities = todayService.findMyTodayContents(userId);
        if (todayEntities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(todayEntities);
    }

    @ApiOperation(value = "오운완 글 삭제")
    @DeleteMapping("/delete/{todaySq}")
    public ResponseEntity<Void> delete(@PathVariable("todaySq") Long todaySq, Authentication authentication) {
        String username = authentication.getName();
        UserEntity user = userRepository.findByUserId(username)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));
        todayService.deleteByTodaySq(todaySq);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "오운완 상세보기")
    @GetMapping("/todayDetail/{todaysq}")
    public ResponseEntity<TodayEntity> todaysq(@PathVariable("todaysq") Long todaysq) {
        Optional<TodayEntity> todays = todayService.findbytodaysq(todaysq);
        return todays.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ApiOperation(value = "오운완 게시물 작성")
    @PostMapping("/create")
    public ResponseEntity<TodayEntity> createTodayPost(
            @RequestPart("content") String content,
            @RequestPart("file") MultipartFile file,
            @RequestHeader("Authorization") String authorizationHeader) {

        String jwtToken = jwtUtil.extractTokenFromHeader(authorizationHeader);
        if (jwtToken == null || !jwtUtil.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId = jwtUtil.getUserId(jwtToken);
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));
        Date created = new Date();
        TodayEntity todayPost = todayService.createTodayPost(content, file, created, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(todayPost);
    }


    @ApiOperation(value = "오운완 글 수정")
    @PutMapping("/update/{id}")
    public ResponseEntity<TodayEntity> updateTodayPost(
            @PathVariable("id") Long todayId,
            @RequestPart("content") String content,
            @RequestPart(value = "file", required = false) MultipartFile file,
            Authentication authentication) {
        String username = authentication.getName();
        UserEntity user = userRepository.findByUserId(username)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));
        // 현재 날짜와 시간 설정
        Date updatedDate = new Date();
        try {
            TodayEntity updatedEntity = todayService.updateEntity(todayId, content, file, updatedDate ,user);
            return ResponseEntity.ok(updatedEntity);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden 응답
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found 응답
        }
    }



}
