package com.example.healthylife.controller;

import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.service.CommunityService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/community")
@RestController
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;

    @GetMapping("/all")
    public List<CommunityEntity> communityList() {
        // validate
        // (http)param binding
        // service call
        List<CommunityEntity> communityEntities = communityService.communityList();
        // return result
        return communityEntities;
    }

    @PostMapping("/register")
    public CommunityEntity register(@RequestBody CommunityEntity communityEntity) {
        return communityService.registerCommunity(communityEntity);
    }
    @PostMapping("/update")
    public CommunityEntity update(@RequestBody CommunityEntity communityEntity) {
        return communityService.updateCommunity(communityEntity);
    }

    @PostMapping("/delete")
    public Boolean delete(@RequestParam long communitySq) {
         communityService.deleteBySq(communitySq);
         return true;
    }
}
