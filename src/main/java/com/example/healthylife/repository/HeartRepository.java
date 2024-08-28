package com.example.healthylife.repository;


import com.example.healthylife.entity.HeartEntity;
import com.example.healthylife.entity.TodayEntity;
import com.example.healthylife.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<HeartEntity, Long> {
    Optional<HeartEntity> findByUserAndToday(UserEntity user, TodayEntity today);
    boolean existsByUserAndToday(UserEntity user, TodayEntity today);

}
