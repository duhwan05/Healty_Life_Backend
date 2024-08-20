package com.example.healthylife.repository;

import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.entity.TodayCommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodayCommentsRepository extends JpaRepository<TodayCommentsEntity,Long> {

    //내가 작성한 글 조회하(유저아이디로)
    List<TodayCommentsEntity> findByUserUserId(String userId);

    List<TodayCommentsEntity> findByTodayEntity_todaySq(long todaySq);
}
