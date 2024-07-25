package com.example.healthylife.service;

import com.example.healthylife.entity.TodayCommentsEntity;

import java.util.List;

public interface TodayCommentsService {
    List<TodayCommentsEntity> todayCommentsList();
    TodayCommentsEntity insertTodayComments(TodayCommentsEntity todayCommentsEntity);
    void deleteByTodayCommentsSq(long todayCommentsSq);
}
