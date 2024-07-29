package com.example.healthylife.repository;

import com.example.healthylife.entity.TodayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodayRepository extends JpaRepository<TodayEntity, Long> {
// 오운완 내가 작성한 글 조회
    List<TodayEntity> findByUserUserId(String userId);
    //jpa 규칙 : findBy 라는 이름으로 시작해야 jpa 가 올바르게 쿼리를 생성
    //          규칙에 맞지 않으면 메소드 생성 X 오류남
}
