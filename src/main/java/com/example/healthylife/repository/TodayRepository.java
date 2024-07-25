package com.example.healthylife.repository;

import com.example.healthylife.entity.TodayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodayRepository extends JpaRepository<TodayEntity, Long> {
}
