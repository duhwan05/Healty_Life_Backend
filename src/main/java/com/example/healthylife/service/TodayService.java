package com.example.healthylife.service;

import com.example.healthylife.entity.TodayEntity;

import java.util.List;

public interface TodayService {
    //오운완 전체조회
    List<TodayEntity> todayList();

    //오운완 등록
    TodayEntity registerToday(TodayEntity todayEntity);
    //오운완 수정
    TodayEntity updateEntity(TodayEntity todayEntity);
    //오운완 삭제
    void deleteByTodaySq(long todaySq);

}
