package com.example.healthylife.service;

import com.example.healthylife.entity.CommunityCommentsEntity;
import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.entity.CommunityRecommendEntity;
import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.repository.CommunityRecommendRepository;
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
    private final CommunityRecommendRepository communityRecommendRepository;
    private final UserRepository userRepository;
    private final CommunityCommentsService communityCommentsService;

    // 글 전체 조회
    public List<CommunityEntity> communityList() {
        return communityRepository.findAll();
    }

    // 커뮤니티 글 작성
    public CommunityEntity registerCommunity(CommunityEntity communityEntity) {
        return communityRepository.save(communityEntity);
    }

    // 커뮤니티 글 수정
    @Transactional
    public CommunityEntity updateCommunity(Long communityId, CommunityEntity updatedCommunityEntity, String username) {
        UserEntity user = userRepository.findByUserId(username)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));
        CommunityEntity existingCommunity = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("커뮤니티 글이 없습니다."));
        // 권한 체크: 현재 로그인한 사용자가 작성자와 동일한지 확인
        if (!existingCommunity.getUser().getUserId().equals(username)) {
            throw new SecurityException("작성자만 수정할 수 있습니다.");
        }
        // 커뮤니티 글의 내용 업데이트
        if (updatedCommunityEntity.getCommunityTitle() != null) {
            existingCommunity.setCommunityTitle(updatedCommunityEntity.getCommunityTitle());
        }
        if (updatedCommunityEntity.getCommunityContents() != null) {
            existingCommunity.setCommunityContents(updatedCommunityEntity.getCommunityContents());
        }
        return communityRepository.save(existingCommunity);
    }

    // 커뮤니티 글 삭제
    public void deleteBySq(long communitySq) {
        communityRepository.deleteById(communitySq);
    }

    // 커뮤니티 내가 쓴 글 조회
    public List<CommunityEntity> findMyContents(String userId) {
        return communityRepository.findByUserUserId(userId);
    }

    // 커뮤니티 단일 조회
    public Optional<CommunityEntity> findCommunityBySq(Long communitySq) {
        Optional<CommunityEntity> community = communityRepository.findByCommunitySq(communitySq);
        community.ifPresent(c -> {
            List<CommunityCommentsEntity> comments = communityCommentsService.getCommentsByCommunitySq(communitySq);
            c.setComments(comments);
        });
        return community;
    }

    // 커뮤니티 추천수
    @Transactional
    public long toggleRecommendation(Long communitySq, Long userId) {
        // 커뮤니티 및 사용자 엔티티 조회
        CommunityEntity community = communityRepository.findById(communitySq)
                .orElseThrow(() -> new RuntimeException("커뮤니티가 없습니다."));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));

        // 추천 여부 확인
        boolean alreadyRecommended = communityRecommendRepository.existsByCommunityAndUser(community, user);

        if (alreadyRecommended) {
            CommunityRecommendEntity recommendation = communityRecommendRepository.findByCommunityAndUser(community, user)
                    .orElseThrow(() -> new RuntimeException("추천 기록이 없습니다."));
            communityRecommendRepository.delete(recommendation);
            community.decrementRecommendationCount();
        } else {
            CommunityRecommendEntity recommendation = new CommunityRecommendEntity(community, user);
            communityRecommendRepository.save(recommendation);
            community.incrementRecommendationCount();
        }

        // 업데이트된 커뮤니티 저장
        CommunityEntity updatedCommunity = communityRepository.save(community);
        return updatedCommunity.getCommunityRecommend();
    }

    // 사용자가 특정 커뮤니티 글에 대해 추천했는지 여부 확인
    public boolean hasUserRecommended(Long communitySq, Long userId) {
        // 커뮤니티 및 사용자 엔티티 조회
        CommunityEntity community = communityRepository.findById(communitySq)
                .orElseThrow(() -> new RuntimeException("커뮤니티가 없습니다."));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));
        // 추천 여부 확인
        return communityRecommendRepository.existsByCommunityAndUser(community, user);
    }


    // 커뮤니티 조회수
    @Transactional
    public void incrementview(Long communitySq) {
        communityRepository.incrementCommunityview(communitySq);
    }
}