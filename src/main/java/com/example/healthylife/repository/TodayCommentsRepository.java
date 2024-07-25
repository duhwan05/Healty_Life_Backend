package com.example.healthylife.repository;

import com.example.healthylife.entity.TodayCommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodayCommentsRepository extends JpaRepository<TodayCommentsEntity,Long> {
}
