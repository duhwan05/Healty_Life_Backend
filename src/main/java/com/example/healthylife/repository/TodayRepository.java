package com.example.healthylife.repository;

import com.example.healthylife.entity.TodayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodayRepository extends JpaRepository<TodayEntity, Long> {
    // 오운완 내가 작성한 글 조회
    List<TodayEntity> findByUserUserId(String userId);

    //today 시퀀스 단일 조화
    Optional<TodayEntity> findByTodaySq(long todaySq);

    Optional<TodayEntity> findByTodaySq(Long todaySq);
}
