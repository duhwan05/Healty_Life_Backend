package com.example.healthylife.service;


import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface CommunityService {
    //글전체조회
    List<CommunityEntity> communityList();
    //글등록
    CommunityEntity registerCommunity(CommunityEntity communityEntity);
    //글 수정
    CommunityEntity updateCommunity(CommunityEntity communityEntity);
    //글삭제
    void deleteBySq(long communitySq);

    List<CommunityEntity> findMyContents(String userId);

}
