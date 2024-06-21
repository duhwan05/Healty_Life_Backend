package com.example.healthylife.repository;

import com.example.healthylife.entity.CommunityCommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityCommentsRepository extends JpaRepository<CommunityCommentsEntity,Long> {
}
