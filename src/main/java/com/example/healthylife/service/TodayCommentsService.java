package com.example.healthylife.service;

import com.example.healthylife.entity.TodayCommentsEntity;
import com.example.healthylife.repository.TodayCommentsRepository;
import com.example.healthylife.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class TodayCommentsService {
    private final TodayCommentsRepository todayCommentsRepository;
    private final UserRepository userRepository;


    public List<TodayCommentsEntity> todayCommentsList() {
        return todayCommentsRepository.findAll();
    }

    public TodayCommentsEntity insertTodayComments(TodayCommentsEntity todayCommentsEntity) {
        return todayCommentsRepository.save(todayCommentsEntity);
    }

    public void deleteByTodayCommentsSq(long todayCommentsSq) {
        todayCommentsRepository.deleteById(todayCommentsSq);

    }

    public List<TodayCommentsEntity> findMyTodayComments(String userId) {
        return todayCommentsRepository.findByUserUserId(userId);
    }
}
