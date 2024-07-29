package com.example.healthylife.service.Impl;

import com.example.healthylife.entity.TodayCommentsEntity;
import com.example.healthylife.repository.TodayCommentsRepository;
import com.example.healthylife.repository.UserRepository;
import com.example.healthylife.service.TodayCommentsService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TodayCommentsServiceImpl implements TodayCommentsService {
    private final TodayCommentsRepository todayCommentsRepository;
    private final UserRepository userRepository;
    public TodayCommentsServiceImpl(TodayCommentsRepository todayCommentsRepository, UserRepository userRepository) {
        this.todayCommentsRepository = todayCommentsRepository;
        this.userRepository = userRepository;
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

    @Override
    public List<TodayCommentsEntity> findMyTodayComments(String userId) {
        return todayCommentsRepository.findByUserUserId(userId);
    }
}
