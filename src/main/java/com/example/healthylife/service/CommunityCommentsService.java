package com.example.healthylife.service;

import com.example.healthylife.entity.CommunityCommentsEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommunityCommentsService {

    //댓글 전체 리스트
    List<CommunityCommentsEntity> communityCommentsList();
    //댓글등록
    CommunityCommentsEntity insertComments(CommunityCommentsEntity communityCommentsEntity);

    //댓글삭제
    void deleteBySq(long commentsSq);
}
