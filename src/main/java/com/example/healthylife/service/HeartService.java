package com.example.healthylife.service;

import com.example.healthylife.entity.HeartEntity;
import com.example.healthylife.entity.TodayEntity;
import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.repository.HeartRepository;
import com.example.healthylife.repository.TodayRepository;
import com.example.healthylife.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HeartService {

    private final HeartRepository heartRepository;
    private final TodayRepository todayRepository;
    private final UserRepository userRepository;

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
            today.incrementLikeCount();
        }
        todayRepository.save(today);
    }

    // 사용자가 특정 오늘의 글에 대해 좋아요를 눌렀는지 여부 확인
    public boolean hasUserLiked(Long todaySq, Long userSq) {
        // 오늘의 글 및 사용자 엔티티 조회
        TodayEntity today = todayRepository.findById(todaySq)
                .orElseThrow(() -> new RuntimeException("오늘의 글이 없습니다."));
        UserEntity user = userRepository.findById(userSq)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));

        Optional<HeartEntity> heart = heartRepository.findByUserAndToday(user, today);

        return heart.map(HeartEntity::getStatus).orElse(false);
    }
}
