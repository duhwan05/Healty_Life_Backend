package com.example.healthylife.service;

import com.example.healthylife.entity.CommunityCommentsEntity;
import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.repository.CommunityCommentsRepository;
import com.example.healthylife.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityCommentsService {

    private final CommunityCommentsRepository communityCommentsRepository;
    private final UserRepository userRepository;

    // 커뮤니티 댓글 작성
    public CommunityCommentsEntity insertComments(CommunityCommentsEntity communityCommentsEntity, String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        communityCommentsEntity.setUser(user); // UserEntity 설정
        return communityCommentsRepository.save(communityCommentsEntity);
    }

    // 커뮤니티 댓글 수정
    public CommunityCommentsEntity updateComments(Long commentsSq, CommunityCommentsEntity updatedCommunityCommentsEntity, String userId) {
        CommunityCommentsEntity existingComment = communityCommentsRepository.findById(commentsSq)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        // 댓글 작성자와 현재 사용자가 일치하는지 확인
        if (!existingComment.getUser().getUserId().equals(userId)) {
            throw new AccessDeniedException("해당 댓글을 수정할 권한이 없습니다.");
        }

        // 필요한 경우, 업데이트할 필드들 설정
        existingComment.setCommunityCommentsContents(updatedCommunityCommentsEntity.getCommunityCommentsContents());
        existingComment.setCommunityCommentsCreated(updatedCommunityCommentsEntity.getCommunityCommentsCreated());

        return communityCommentsRepository.save(existingComment);
    }

    // 댓글 삭제
    public void deleteBySq(Long commentsSq, String userId) {
        CommunityCommentsEntity existingComment = communityCommentsRepository.findById(commentsSq)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        // 댓글 작성자와 현재 사용자가 일치하는지 확인
        if (!existingComment.getUser().getUserId().equals(userId)) {
            throw new AccessDeniedException("해당 댓글을 삭제할 권한이 없습니다.");
        }
        communityCommentsRepository.delete(existingComment);
    }

    // 추후 리팩토링 시 삭제예정
    public List<CommunityCommentsEntity> getCommentsByCommunitySq(Long communitySq) {
        return communityCommentsRepository.findByCommunityCommunitySq(communitySq);
    }
}
