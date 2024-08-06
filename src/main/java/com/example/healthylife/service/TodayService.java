package com.example.healthylife.service;

import com.example.healthylife.entity.TodayEntity;
import com.example.healthylife.repository.TodayRepository;
import com.example.healthylife.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class TodayService {

    private final TodayRepository todayRepository;
    private final UserRepository userRepository;


    public List<TodayEntity> todayList() {
       return todayRepository.findAll();
    }

    public List<TodayEntity> findMyTodayContents(String userId) {
        return todayRepository.findByUserUserId(userId);
    }

    //오운완 내가 쓴 글 찾기
//    @Override
//    public List<TodayEntity> findMyToday(String userId) {
//        return todayRepository.findByUserToday(userId);
//    }

    public TodayEntity registerToday(TodayEntity todayEntity) {
        return todayRepository.save(todayEntity);
    }

    public TodayEntity updateEntity(TodayEntity todayEntity) {
        return todayRepository.save(todayEntity);
    }

    public void deleteByTodaySq(long todaySq) {
todayRepository.deleteById(todaySq);
    }


}
