package com.example.healthylife.service;

import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.entity.HeartEntity;
import com.example.healthylife.entity.TodayEntity;
import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.repository.HeartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class HeartService {
    @Autowired
    private HeartRepository heartRepository;

    public void Like(UserEntity user, TodayEntity today) {
        Optional<HeartEntity> existingHeart = heartRepository.findByUserAndToday(user, today);

        if (existingHeart.isPresent()) {
            HeartEntity heart = existingHeart.get();
            heart.toggleStatus();
            heartRepository.save(heart);
        } else {
            HeartEntity heart = HeartEntity.builder()
                    .user(user)
                    .today(today)
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .build();
            heartRepository.save(heart);
        }
    }

    public Long HeartCount(TodayEntity today) {
        return heartRepository.HeartCount(today);
    }
}
