package com.example.healthylife.controller;

import com.example.healthylife.entity.CommunityCommentsEntity;
import com.example.healthylife.service.CommunityCommentsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/communityComments")
@RestController
@RequiredArgsConstructor
public class CommunityCommentsController {

    private final CommunityCommentsService communityCommentsService;

    @ApiOperation(value = "커뮤니티 댓글 작성")
    @PostMapping("/insert")
    public ResponseEntity<CommunityCommentsEntity> insert(@RequestBody CommunityCommentsEntity communityCommentsEntity, Authentication authentication) {
        String userId = authentication.getName();
        CommunityCommentsEntity savedComment = communityCommentsService.insertComments(communityCommentsEntity, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @ApiOperation(value = "커뮤니티 댓글 수정")
    @PutMapping("/update")
    public ResponseEntity<CommunityCommentsEntity> update(@RequestBody CommunityCommentsEntity updatedCommunityCommentsEntity, Authentication authentication) {
        String userId = authentication.getName();

        try {
            CommunityCommentsEntity updatedComment = communityCommentsService.updateComments(
                    updatedCommunityCommentsEntity.getCommunityCommentsSq(),
                    updatedCommunityCommentsEntity,
                    userId
            );
            return ResponseEntity.ok(updatedComment);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiOperation(value = "커뮤니티 댓글 삭제")
    @DeleteMapping("/delete/{commentsSq}")
    public ResponseEntity<Void> delete(@PathVariable("commentsSq") Long commentsSq, Authentication authentication) {
        String userId = authentication.getName(); // 인증된 사용자의 userId

        try {
            communityCommentsService.deleteBySq(commentsSq, userId);
            return ResponseEntity.noContent().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
