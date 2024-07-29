package com.example.healthylife.repository;

import com.example.healthylife.entity.CommunityCommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityCommentsRepository extends JpaRepository<CommunityCommentsEntity,Long> {
    List<CommunityCommentsEntity> findByUserUserId(String userId);
    }
