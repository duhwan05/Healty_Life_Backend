package com.example.healthylife.controller;

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

    @ApiOperation(value = "커뮤니티 글 전체 조회")
    @GetMapping("/all")
    public List<CommunityEntity> communityList() {
        // validate
        // (http)param binding
        // service call
        List<CommunityEntity> communityEntities = communityService.communityList();
        // return result
        return communityEntities;
    }


    //커뮤니티 단일조회 (userId가지고 내가 쓴 글 조회)
    @ApiOperation(value = "커뮤니티 내가 쓴 글 조회")
    @GetMapping("/myCommunityContents")
    public List<CommunityEntity> myCommunityContentsList(@RequestParam String userId) {
        return communityService.findMyContents(userId);
    }

    @ApiOperation(value = "커뮤니티 글 작성")
    @PostMapping("/register")
    public CommunityEntity register(@RequestBody CommunityEntity communityEntity) {
        return communityService.registerCommunity(communityEntity);
    }

    @ApiOperation(value = "커뮤니티 글 수정")
    @PostMapping("/update")
    public CommunityEntity update(@RequestBody CommunityEntity communityEntity) {
        return communityService.updateCommunity(communityEntity);
    }


    @ApiOperation(value = "커뮤니티 글 삭제")
    @PostMapping("/delete")
    public Boolean delete(@RequestParam long communitySq) {
        communityService.deleteBySq(communitySq);
        return true;
    }

    @ApiOperation(value = "커뮤니티 단일조회")
    @GetMapping("/communitySq")
    public ResponseEntity<CommunityEntity> getCommunityBySq(@RequestParam Long communitySq) {
        try {
            Optional<CommunityEntity> community = communityService.findCommunityBySq(communitySq);
            if (community.isPresent()) {
                return new ResponseEntity<>(community.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // 로그를 남기거나 필요한 조치를 취합니다
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 커뮤니티 글 조회수
    @ApiOperation(value = "커뮤니티 글 추천")
    @PostMapping("/recommend")
    public ResponseEntity<Void> recommendCommunity(@RequestParam Long sq) {
        communityService.incrementRecommend(sq);
        return ResponseEntity.ok().build();
    }
}
