package com.example.healthylife.repository;


import com.example.healthylife.entity.HeartEntity;
import com.example.healthylife.entity.TodayEntity;
import com.example.healthylife.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<HeartEntity, Long> {
    Optional<HeartEntity> findByUserAndToday(UserEntity user, TodayEntity todayentity);

    @Query("SELECT COUNT(h) FROM HeartEntity h WHERE h.today = :today AND h.status = true")
    Long HeartCount(@Param("today") TodayEntity today);
}
