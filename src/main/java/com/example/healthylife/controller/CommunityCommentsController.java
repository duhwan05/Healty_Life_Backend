package com.example.healthylife.controller;

import com.example.healthylife.entity.CommunityCommentsEntity;
import com.example.healthylife.service.CommunityCommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/communityComments")
@RestController
@RequiredArgsConstructor
public class CommunityCommentsController {

    private final CommunityCommentsService communityCommentsService;

    @GetMapping("/all")
    public List<CommunityCommentsEntity> communityCommentsList(){
        List<CommunityCommentsEntity> communityCommentsEntities = communityCommentsService.communityCommentsList();
        return communityCommentsEntities;
    }
    @PostMapping("/insert")
    public CommunityCommentsEntity insert(@RequestBody CommunityCommentsEntity communityCommentsEntity){
        return communityCommentsService.insertComments(communityCommentsEntity);

    }
    @PostMapping("/delete")
    public Boolean delete(@RequestParam long commentsSq){
        communityCommentsService.deleteBySq(commentsSq);
        return true;
    }
}
