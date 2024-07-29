package com.example.healthylife.controller;

import com.example.healthylife.entity.TodayCommentsEntity;
import com.example.healthylife.service.TodayCommentsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todayComments")
@RequiredArgsConstructor
public class TodayCommentsController {
    private final TodayCommentsService todayCommentsService;


    @ApiOperation(value = "오운완 댓글 전체조회")
    @GetMapping("/todayCommentsAll")
    public List<TodayCommentsEntity> todayCommentsList (){
        return todayCommentsService.todayCommentsList();
    }

    @ApiOperation(value = "오운완 댓글 작성")
    @PostMapping("/register")
    public TodayCommentsEntity register(@RequestBody TodayCommentsEntity todayCommentsEntity){
        return todayCommentsService.insertTodayComments(todayCommentsEntity);
    }



    @ApiOperation(value = "오운완 내가 쓴 댓글 조회")
    @GetMapping("/myTodayCommentsContents")
    public List<TodayCommentsEntity> myTodayCommentsContents(@RequestParam String userId){
        return todayCommentsService.findMyTodayComments(userId);

    }

    @ApiOperation(value = "오운완 댓글 삭제")
    @PostMapping("/todayCommentsDelete")
    public Boolean todayCommentsDelete(@RequestParam long todayCommentsSq){
        todayCommentsService.deleteByTodayCommentsSq(todayCommentsSq);
        return true;
    }

}
