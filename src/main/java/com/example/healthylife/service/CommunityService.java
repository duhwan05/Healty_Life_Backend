package com.example.healthylife.service;

import com.example.healthylife.entity.CommunityCommentsEntity;
import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.repository.CommunityRepository;
import com.example.healthylife.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityCommentsService communityCommentsService;


    //글전체조회
    public List<CommunityEntity> communityList() {
        return communityRepository.findAll();
    }

    //커뮤니티 글 작성
    public CommunityEntity registerCommunity(CommunityEntity communityEntity) {
        return communityRepository.save(communityEntity);
    }

    //커뮤니티 글 수정
    @Transactional
    public CommunityEntity updateCommunity(CommunityEntity communityEntity) {
        return communityRepository.save(communityEntity);
    }

    //커뮤니티 글 삭제
    public void deleteBySq(long communitySq) {
        communityRepository.deleteById(communitySq);
    }

    //커뮤니티 내가 쓴글 조회
    public List<CommunityEntity> findMyContents(String userId) {
        return communityRepository.findByUserUserId(userId);
    }

    //커뮤니티 단일조회
    public Optional<CommunityEntity> findCommunityBySq(Long communitySq) {
        Optional<CommunityEntity> community = communityRepository.findByCommunitySq(communitySq);
        community.ifPresent(c -> {
            List<CommunityCommentsEntity> comments = communityCommentsService.getCommentsByCommunitySq(communitySq);
            c.setComments(comments);
        });
        return community;
    }

    //커뮤니티 추천수
    @Transactional
    public long toggleRecommendation(Long sq) {
        CommunityEntity community = communityRepository.findById(sq)
                .orElseThrow(() -> new RuntimeException("커뮤니티가 없습니다."));

        boolean isRecommended = community.getCommunityRecommend() > 0;
        community.toggleRecommendation(isRecommended);
        CommunityEntity updatedCommunity = communityRepository.save(community);
        return updatedCommunity.getCommunityRecommend();
    }

    // 커뮤니티 조회수
    @Transactional
    public void incrementview(Long communitySq) {
        communityRepository.incrementCommunityview(communitySq);
    }


}

