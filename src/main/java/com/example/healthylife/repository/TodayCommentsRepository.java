package com.example.healthylife.repository;

import com.example.healthylife.entity.TodayCommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodayCommentsRepository extends JpaRepository<TodayCommentsEntity,Long> {

    //내가 작성한 글 조회하(유저아이디로)
    List<TodayCommentsEntity> findByUserUserId(String userId);

    List<TodayCommentsEntity> findByTodayCommentsSq(Long todayCommentsSq);
}
