package com.example.healthylife.controller;

import com.example.healthylife.config.jwt.JwtUtil;
import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.service.CommunityService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequestMapping("/community")
@RestController
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;
    private final JwtUtil jwtUtil;

    @ApiOperation(value = "커뮤니티 글 전체 조회")
    @GetMapping("/all")
    public List<CommunityEntity> communityList() {
        List<CommunityEntity> communityEntities = communityService.communityList();
        return communityEntities;
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
    public CommunityEntity register(@RequestBody CommunityEntity communityEntity) {
        return communityService.registerCommunity(communityEntity);
    }

    @ApiOperation(value = "커뮤니티 글 수정")
    @PutMapping("/update")
    public CommunityEntity update(@RequestBody CommunityEntity communityEntity) {
        return communityService.updateCommunity(communityEntity);
    }

    @ApiOperation(value = "커뮤니티 글 삭제")
    @DeleteMapping("/delete/{communitySq}")
    public ResponseEntity<Void> delete(@PathVariable("communitySq") Long communitySq) {
        communityService.deleteBySq(communitySq);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "커뮤니티 상세보기")
    @GetMapping("/communityDetail/{communitySq}")
    public ResponseEntity<CommunityEntity> getCommunityBySq(@PathVariable("communitySq") Long communitySq) {
        try {
            Optional<CommunityEntity> community = communityService.findCommunityBySq(communitySq);
            if (community.isPresent()) {
                return new ResponseEntity<>(community.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "커뮤니티 글 추천수")
    @PostMapping("/recommend/{sq}")
    public ResponseEntity<?> communityRecommend(@PathVariable("sq") Long sq) {
        long updatedCount = communityService.toggleRecommendation(sq);
        return ResponseEntity.ok(updatedCount);
    }

    @ApiOperation(value = "커뮤니티 글 조회수")
    @PostMapping("/view/{sq}")
    public ResponseEntity<Void> Communityview(@PathVariable("sq") Long sq) {
        communityService.incrementview(sq);
        return ResponseEntity.ok().build();
    }
}
