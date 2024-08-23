package com.example.healthylife.service;

import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.entity.HeartEntity;
import com.example.healthylife.entity.TodayEntity;
import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.repository.HeartRepository;
import com.example.healthylife.repository.TodayRepository;
import com.example.healthylife.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class HeartService {
    private final HeartRepository heartRepository;
    private final TodayRepository todayRepository;
    private final UserRepository userRepository;

    public HeartService(HeartRepository heartRepository, TodayRepository todayRepository, UserRepository userRepository) {
        this.heartRepository = heartRepository;
        this.todayRepository = todayRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void toggleLike(UserEntity user, TodayEntity today) {
        Optional<HeartEntity> existingHeart = heartRepository.findByUserAndToday(user, today);

        if (existingHeart.isPresent()) {
            HeartEntity heart = existingHeart.get();
            heart.toggleStatus(); // 좋아요 상태를 토글
            heartRepository.save(heart);

            if (heart.getStatus()) {
                today.incrementLikeCount(); // 좋아요 수 증가
            } else {
                today.decrementLikeCount(); // 좋아요 수 감소
            }
        } else {
            HeartEntity heart = HeartEntity.builder()
                    .user(user)
                    .today(today)
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .build();
            heartRepository.save(heart);
            today.incrementLikeCount(); // 새로운 좋아요가 추가된 경우 좋아요 수 증가
        }

        // 오늘의 글에 대한 변경 사항 저장
        todayRepository.save(today);
    }

    // 사용자가 특정 오늘의 글에 대해 좋아요를 눌렀는지 여부 확인
    public boolean hasUserLiked(Long todaySq) {
        // 인증된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // 오늘의 글 및 사용자 엔티티 조회
        TodayEntity today = todayRepository.findById(todaySq)
                .orElseThrow(() -> new RuntimeException("오늘의 글이 없습니다."));
        UserEntity user = userRepository.findByUserId(username)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));

        // 좋아요 여부 확인
        return heartRepository.existsByUserAndToday(user, today);
    }
}
