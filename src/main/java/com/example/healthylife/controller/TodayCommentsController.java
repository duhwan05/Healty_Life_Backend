package com.example.healthylife.controller;

import com.example.healthylife.entity.TodayCommentsEntity;
import com.example.healthylife.service.TodayCommentsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/todayComments")
@RequiredArgsConstructor
public class TodayCommentsController {

    private final TodayCommentsService todayCommentsService;

    @ApiOperation(value = "오운완 댓글 작성")
    @PostMapping("/register")
    public ResponseEntity<TodayCommentsEntity> register(@RequestBody TodayCommentsEntity todayCommentsEntity, Authentication authentication){
        String userId = authentication.getName();
        TodayCommentsEntity savedComment = todayCommentsService.insertTodayComments(todayCommentsEntity,userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @ApiOperation(value = "오운완 댓글 수정")
    @PostMapping("/update")
    public ResponseEntity<TodayCommentsEntity> todayCommentsUpdate(@RequestBody TodayCommentsEntity updateTodayCommentsEntity, Authentication authentication){
        String userId = authentication.getName();
        try {
            TodayCommentsEntity updatedComment = todayCommentsService.updateComments(
                    updateTodayCommentsEntity.getTodayCommentsSq(),
                    updateTodayCommentsEntity,
                    userId
            );
            return ResponseEntity.ok(updatedComment);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiOperation(value = "오운완 댓글 삭제")
    @PostMapping("/delete/{todayCommentSq}")
    public ResponseEntity<Void> todayCommentsDelete(@PathVariable("todayCommentSq") Long todayCommentsSq, Authentication authentication){
        String userId =  authentication.getName();

        try {
            todayCommentsService.deleteByTodayCommentsSq(todayCommentsSq,userId);
            return ResponseEntity.noContent().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //    @ApiOperation(value = "오운완 내가 쓴 댓글 조회")
    //    @GetMapping("/myTodayCommentsContents")
    //    public List<TodayCommentsEntity> myTodayCommentsContents(@RequestParam String userId){
    //        return todayCommentsService.findMyTodayComments(userId);
    //
    //    }

}
