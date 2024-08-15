package com.example.healthylife.controller;

import com.example.healthylife.config.jwt.JwtUtil;
import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.entity.CommunityRecommendEntity;
import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.repository.CommunityRecommendRepository;
import com.example.healthylife.repository.CommunityRepository;
import com.example.healthylife.repository.UserRepository;
import com.example.healthylife.service.CommunityService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/community")
@RestController
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final CommunityRecommendRepository communityRecommendRepository;
    private final CommunityRepository communityRepository;

    @ApiOperation(value = "커뮤니티 글 전체 조회")
    @GetMapping("/all")
    public List<CommunityEntity> communityList() {
        return communityService.communityList();
    }

    @ApiOperation(value = "커뮤니티 내가 쓴 글 조회")
    @GetMapping("/myCommunityContents")
    public ResponseEntity<List<CommunityEntity>> myCommunityContentsList(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = jwtUtil.extractTokenFromHeader(authorizationHeader);
        if (jwtToken == null || !jwtUtil.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId = jwtUtil.getUserId(jwtToken);
        List<CommunityEntity> communityEntities = communityService.findMyContents(userId);
        if (communityEntities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(communityEntities);
    }

    @ApiOperation(value = "커뮤니티 글 작성")
    @PostMapping("/register")
    public ResponseEntity<CommunityEntity> register(@RequestBody CommunityEntity communityEntity, Authentication authentication) {
        String username = authentication.getName();
        UserEntity user = userRepository.findByUserId(username)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));
        // 커뮤니티 글 작성자 설정
        communityEntity.setUser(user);
        // 커뮤니티 글 작성
        CommunityEntity savedCommunity = communityService.registerCommunity(communityEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCommunity);
    }


    @ApiOperation(value = "커뮤니티 글 수정")
    @PutMapping("/update")
    public ResponseEntity<CommunityEntity> update(@RequestBody CommunityEntity updatedCommunityEntity, Authentication authentication) {
        String username = authentication.getName(); // 인증된 사용자의 이름 (ID)

        try {
            CommunityEntity updatedCommunity = communityService.updateCommunity(
                    updatedCommunityEntity.getCommunitySq(),
                    updatedCommunityEntity,
                    username
            );
            return ResponseEntity.ok(updatedCommunity);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden 응답
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found 응답
        }
    }


    @ApiOperation(value = "커뮤니티 글 삭제")
    @DeleteMapping("/delete/{communitySq}")
    public ResponseEntity<Void> delete(@PathVariable("communitySq") Long communitySq, Authentication authentication) {
        String username = authentication.getName(); // 인증된 사용자의 이름 (ID)
        UserEntity user = userRepository.findByUserId(username)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));
        // 권한 체크 또는 작성자 확인 로직 추가
        communityService.deleteBySq(communitySq);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "커뮤니티 상세보기")
    @GetMapping("/communityDetail/{communitySq}")
    public ResponseEntity<CommunityEntity> getCommunityBySq(@PathVariable("communitySq") Long communitySq) {
        Optional<CommunityEntity> community = communityService.findCommunityBySq(communitySq);
        return community.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ApiOperation(value = "커뮤니티 글 추천수")
    @PostMapping("/recommend/{communitySq}")
    public ResponseEntity<?> communityRecommend(@PathVariable("communitySq") Long communitySq,
                                                @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = jwtUtil.extractTokenFromHeader(authorizationHeader);
        if (jwtToken == null || !jwtUtil.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = jwtUtil.getUserId(jwtToken);
        UserEntity user = userRepository.findByUserId(username)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));

        long updatedCount = communityService.toggleRecommendation(communitySq, user.getUserSq());
        return ResponseEntity.ok(updatedCount);
    }

    @ApiOperation(value = "커뮤니티 글 추천 여부 확인")
    @GetMapping("/recommend/check/{communitySq}")
    public ResponseEntity<Map<String, Boolean>> checkRecommendation(
            @PathVariable("communitySq") Long communitySq,
            @RequestHeader("Authorization") String authorizationHeader) {

        // JWT 토큰을 통해 사용자 정보 추출
        String jwtToken = jwtUtil.extractTokenFromHeader(authorizationHeader);
        if (jwtToken == null || !jwtUtil.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 사용자 정보로 유저 ID 가져오기
        String username = jwtUtil.getUserId(jwtToken);
        UserEntity user = userRepository.findByUserId(username)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));

        // 사용자가 해당 커뮤니티 글을 추천했는지 여부 확인
        boolean hasRecommended = communityService.hasUserRecommended(communitySq, user.getUserSq());

        // 추천 여부를 포함한 응답 반환
        return ResponseEntity.ok(Collections.singletonMap("hasRecommended", hasRecommended));
    }


    @ApiOperation(value = "커뮤니티 글 조회수")
    @PostMapping("/view/{sq}")
    public ResponseEntity<Void> communityView(@PathVariable("sq") Long sq) {
        communityService.incrementview(sq);
        return ResponseEntity.ok().build();
    }
}
