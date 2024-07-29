package com.example.healthylife.service.Impl;

import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.repository.CommunityRepository;
import com.example.healthylife.repository.UserRepository;
import com.example.healthylife.service.CommunityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Date;
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;

    public CommunityServiceImpl(CommunityRepository communityRepository, UserRepository userRepository, UserRepository userRepository1) {

        this.communityRepository = communityRepository;


        this.userRepository = userRepository;
    }

    //글전체조회
    @Override
    public List<CommunityEntity> communityList() {
        return communityRepository.findAll();
    }


    //커뮤니티 글 작성
    @Override
    public CommunityEntity registerCommunity(CommunityEntity communityEntity) {
        return communityRepository.save(communityEntity);
    }


    //커뮤니티 글 수정
    @Override
    @Transactional
    public CommunityEntity updateCommunity(CommunityEntity communityEntity) {

        return communityRepository.save(communityEntity);
    }

    //커뮤니티 글 삭제
    @Override
    public void deleteBySq(long communitySq) {

        communityRepository.deleteById(communitySq);
    }

    @Override
    public List<CommunityEntity> findMyContents(String userId) {
        return communityRepository.findByUserUserId(userId);
    }
}
    //커뮤니티 내가 쓴 글 조회
//    @Override
//    public List<CommunityEntity> findMyContents(long userSq) {
//        return communityRepository.findByUserUserId(userSq);
//    }






