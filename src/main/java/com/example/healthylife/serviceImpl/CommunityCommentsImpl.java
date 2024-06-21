package com.example.healthylife.serviceImpl;

import com.example.healthylife.entity.CommunityCommentsEntity;
import com.example.healthylife.repository.CommunityCommentsRepository;
import com.example.healthylife.service.CommunityCommentsService;
import com.example.healthylife.service.CommunityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityCommentsImpl implements CommunityCommentsService {
    private final CommunityCommentsRepository communityCommentsRepository;

    public CommunityCommentsImpl(CommunityCommentsRepository communityCommentsRepository){
        this.communityCommentsRepository=communityCommentsRepository;
    }

    //댓글 전체 리스트
    @Override
    public List<CommunityCommentsEntity> communityCommentsList() {
        return communityCommentsRepository.findAll();
    }

    //댓글등록
    @Override
    public CommunityCommentsEntity insertComments(CommunityCommentsEntity communityCommentsEntity) {
        return communityCommentsRepository.save(communityCommentsEntity);
    }

    //댓글삭제
    @Override
    public void deleteBySq(long commentsSq) {

        communityCommentsRepository.deleteById(commentsSq);
    }
}
