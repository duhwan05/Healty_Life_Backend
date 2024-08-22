package com.example.healthylife.service;

import com.example.healthylife.entity.TodayCommentsEntity;
import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.repository.TodayCommentsRepository;
import com.example.healthylife.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class TodayCommentsService {

    private final TodayCommentsRepository todayCommentsRepository;
    private final UserRepository userRepository;

    // 오운완 댓글 작성
    public TodayCommentsEntity insertTodayComments(TodayCommentsEntity todayCommentsEntity, String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        todayCommentsEntity.setUser(user);
        return todayCommentsRepository.save(todayCommentsEntity);
    }

    // 오운완 댓글 수정
    public TodayCommentsEntity updateComments(Long todayCommentsSq, TodayCommentsEntity updatedTodayCommentsEntity, String userId) {
        TodayCommentsEntity existingComment = todayCommentsRepository.findById(todayCommentsSq)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        if (!existingComment.getUser().getUserId().equals(userId)) {
            throw new AccessDeniedException("해당 댓글을 수정할 권한이 없습니다.");
        }
        existingComment.setTodayCommentsContents(updatedTodayCommentsEntity.getTodayCommentsContents());
        existingComment.setTodayCommentsCreated(updatedTodayCommentsEntity.getTodayCommentsCreated());

        return todayCommentsRepository.save(existingComment);
    }

    // 오운완 댓글 삭제
    public void deleteByTodayCommentsSq(Long todayCommentsSq, String userId) {
        TodayCommentsEntity existingComment = todayCommentsRepository.findById(todayCommentsSq)
                        .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        if(!existingComment.getUser().getUserId().equals(userId)) {
            throw new AccessDeniedException("해당 댓글을 삭제할 권한이 없습니다.");
        }
        todayCommentsRepository.delete(existingComment);
    }

    // 추후 리팩토링 시 삭제예정
    public List<TodayCommentsEntity> findMyTodayComments(String userId) {
        return todayCommentsRepository.findByUserUserId(userId);
    }
}
