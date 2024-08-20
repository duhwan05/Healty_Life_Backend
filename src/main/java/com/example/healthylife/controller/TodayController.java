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

    //오운완 내가 쓴 글 조회
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
        //확실하게 리스트에 담고싶으면 그래도됨
    }

/*    @ApiOperation(value = "오운완 글작성")
    @PostMapping("/register")
    public TodayEntity register (@RequestBody TodayEntity todayEntity){
        return todayService.registerToday(todayEntity);

    }*/

    @ApiOperation(value = "오운완 글 수정")
    @PutMapping("/update")
    public ResponseEntity<TodayEntity> update(@RequestBody TodayEntity updatedTodayEntity, Authentication authentication) {
        String username = authentication.getName(); // 인증된 사용자의 이름 (ID)

        try {
            TodayEntity updatedEntity = todayService.updateEntity(updatedTodayEntity.getTodaySq(),updatedTodayEntity, username);
            return ResponseEntity.ok(updatedEntity);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden 응답
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found 응답
        }
    }

    @ApiOperation(value = "오운완 글 삭제")
    @DeleteMapping("/delete/{todaySq}")
    public ResponseEntity<Void> delete(@PathVariable("todaySq") Long todaySq, Authentication authentication) {
        String username = authentication.getName(); // 인증된 사용자의 이름 (ID)
        UserEntity user = userRepository.findByUserId(username)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));
        // 권한 체크 또는 작성자 확인 로직 추가
        todayService.deleteByTodaySq(todaySq);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "오운완 상세보기")
    @GetMapping("/todayDetail/{todaysq}")
    public ResponseEntity<TodayEntity> todaysq(@PathVariable("todaysq") Long todaysq) {
        Optional<TodayEntity> todays = todayService.findTodayBySq(todaysq);
        return todays.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ApiOperation(value = "투데이 글 작성")
    @PostMapping("/create")
    public ResponseEntity<TodayEntity> createTodayPost(@RequestParam("content") String content,
                                                       @RequestPart("file") MultipartFile file,
                                                       Authentication authentication) {
        String username = authentication.getName();
        UserEntity user = userRepository.findByUserId(username)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));

        // 투데이 글 작성
        TodayEntity todayPost = todayService.createTodayPost(content, file, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(todayPost);
    }


}
