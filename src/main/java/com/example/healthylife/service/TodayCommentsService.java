package com.example.healthylife.service;

import com.example.healthylife.entity.TodayCommentsEntity;

import java.util.List;

public interface TodayCommentsService {
    List<TodayCommentsEntity> todayCommentsList();
    TodayCommentsEntity insertTodayComments(TodayCommentsEntity todayCommentsEntity);
    void deleteByTodayCommentsSq(long todayCommentsSq);

    //오운완 내가 쓴 댓글 확인하기
    List<TodayCommentsEntity> findMyTodayComments(String userId);
}
