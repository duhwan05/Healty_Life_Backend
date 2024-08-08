package com.example.healthylife.repository;


import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Long> {

    public List<CommunityEntity> findByCommunitySqAndCommunityTitle(long communitySq, String communityTitle);
//jpaQUERY
    @Query("SELECT community FROM CommunityEntity community " +
            "WHERE community.communityTitle = :param")
    public CommunityEntity findMyStyleBySQL(String param);

//native query
    @Query(value = "SELECT * FROM community " +
            "WHERE community_title = :param", nativeQuery = true)
    public CommunityEntity findMyStyleByNativeSQL(String param);
//내가 작성한 글 조회
    List<CommunityEntity> findByUserUserId(String userId);

    // 커뮤니티 단일조회
    Optional<CommunityEntity> findByCommunitySq(Long communitySq);

    // 커뮤니티 추천
    @Modifying
    @Query("UPDATE CommunityEntity c SET c.communityRecommend = c.communityRecommend + 1 WHERE c.id = :communitySq")
    void incrementCommunityRecommend(Long communitySq);
}
