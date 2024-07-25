package com.example.healthylife.controller;

import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.service.CommunityService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
}
