package com.example.healthylife.controller;

import com.example.healthylife.entity.CommunityCommentsEntity;
import com.example.healthylife.service.CommunityCommentsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/communityComments")
@RestController
@RequiredArgsConstructor
public class CommunityCommentsController {

    private final CommunityCommentsService communityCommentsService;

    @ApiOperation(value = "커뮤니티 댓글 전체 조회")
    @GetMapping("/all")
    public List<CommunityCommentsEntity> communityCommentsList(){
        List<CommunityCommentsEntity> communityCommentsEntities = communityCommentsService.communityCommentsList();
        return communityCommentsEntities;
    }

    @ApiOperation(value = "커뮤니티 댓글 작성")
    @PostMapping("/insert")
    public CommunityCommentsEntity insert(@RequestBody CommunityCommentsEntity communityCommentsEntity){
        return communityCommentsService.insertComments(communityCommentsEntity);

    }

    @ApiOperation(value = "커뮤니티 댓글 삭제")
    @PostMapping("/delete")
    public Boolean delete(@RequestParam long commentsSq){
        communityCommentsService.deleteBySq(commentsSq);
        return true;
    }
}
