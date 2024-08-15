package com.example.healthylife.repository;

import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.entity.CommunityRecommendEntity;
import com.example.healthylife.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityRecommendRepository extends JpaRepository<CommunityRecommendEntity, Long> {
    boolean existsByCommunityAndUser(CommunityEntity community, UserEntity user);
    Optional<CommunityRecommendEntity> findByCommunityAndUser(CommunityEntity community, UserEntity user);
}
