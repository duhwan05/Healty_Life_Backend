package com.example.healthylife.service.Impl;

import com.example.healthylife.entity.TodayEntity;
import com.example.healthylife.repository.TodayRepository;
import com.example.healthylife.service.TodayService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodayServiceImpl implements TodayService {

    private final TodayRepository todayRepository;

    public TodayServiceImpl(TodayRepository todayRepository){
        this.todayRepository = todayRepository;
    }

    @Override
    public List<TodayEntity> todayList() {
       return todayRepository.findAll();
    }

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
