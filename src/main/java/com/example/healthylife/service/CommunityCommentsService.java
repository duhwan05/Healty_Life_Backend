package com.example.healthylife.service;

import com.example.healthylife.entity.CommunityCommentsEntity;
import com.example.healthylife.repository.CommunityCommentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityCommentsService {
    private final CommunityCommentsRepository communityCommentsRepository;

    public CommunityCommentsService(CommunityCommentsRepository communityCommentsRepository){
        this.communityCommentsRepository=communityCommentsRepository;
    }

    //댓글 전체 리스트
    public List<CommunityCommentsEntity> communityCommentsList() {
        return communityCommentsRepository.findAll();
    }

    //댓글등록
    public CommunityCommentsEntity insertComments(CommunityCommentsEntity communityCommentsEntity) {
        return communityCommentsRepository.save(communityCommentsEntity);
    }

    //댓글삭제
    public void deleteBySq(long commentsSq) {

        communityCommentsRepository.deleteById(commentsSq);
    }

    public List<CommunityCommentsEntity> findMyCommunityComments(String userId) {
        return communityCommentsRepository.findByUserUserId(userId);
    }

    public List<CommunityCommentsEntity> getCommentsByCommunitySq(Long communitySq) {
        return communityCommentsRepository.findByCommunityCommunitySq(communitySq);
    }
}
