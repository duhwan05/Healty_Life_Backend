package com.example.healthylife.service.Impl;

import com.example.healthylife.entity.TodayEntity;
import com.example.healthylife.repository.TodayRepository;
import com.example.healthylife.repository.UserRepository;
import com.example.healthylife.service.TodayService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodayServiceImpl implements TodayService {

    private final TodayRepository todayRepository;
    private final UserRepository userRepository;
    public TodayServiceImpl(TodayRepository todayRepository, UserRepository userRepository){
        this.todayRepository = todayRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TodayEntity> todayList() {
       return todayRepository.findAll();
    }

    @Override
    public List<TodayEntity> findMyTodayContents(String userId) {
        return todayRepository.findByUserUserId(userId);
    }

    //오운완 내가 쓴 글 찾기
//    @Override
//    public List<TodayEntity> findMyToday(String userId) {
//        return todayRepository.findByUserToday(userId);
//    }

    @Override
    public TodayEntity registerToday(TodayEntity todayEntity) {
        return todayRepository.save(todayEntity);
    }

    @Override
    public TodayEntity updateEntity(TodayEntity todayEntity) {
        return todayRepository.save(todayEntity);
    }

    @Override
    public void deleteByTodaySq(long todaySq) {
todayRepository.deleteById(todaySq);
    }


}
