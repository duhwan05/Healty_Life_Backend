package com.example.healthylife.service.Impl;

import com.example.healthylife.entity.TodayCommentsEntity;
import com.example.healthylife.repository.TodayCommentsRepository;
import com.example.healthylife.service.TodayCommentsService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TodayCommentsServiceImpl implements TodayCommentsService {
    private final TodayCommentsRepository todayCommentsRepository;

    public TodayCommentsServiceImpl(TodayCommentsRepository todayCommentsRepository) {
        this.todayCommentsRepository = todayCommentsRepository;
    }

    @Override
    public List<TodayCommentsEntity> todayCommentsList() {
        return todayCommentsRepository.findAll();
    }

    @Override
    public TodayCommentsEntity insertTodayComments(TodayCommentsEntity todayCommentsEntity) {
        return todayCommentsRepository.save(todayCommentsEntity);
    }

    @Override
    public void deleteByTodayCommentsSq(long todayCommentsSq) {
        todayCommentsRepository.deleteById(todayCommentsSq);

    }
}
