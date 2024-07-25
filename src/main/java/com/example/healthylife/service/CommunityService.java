package com.example.healthylife.service;


import com.example.healthylife.entity.CommunityEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommunityService {
    //글전체조회
    List<CommunityEntity> communityList();
    //글등록
    CommunityEntity registerCommunity(CommunityEntity communityEntity);
    //글 수정
    CommunityEntity updateCommunity(CommunityEntity communityEntity);
    //글삭제
    void deleteBySq(long communitySq);


}
