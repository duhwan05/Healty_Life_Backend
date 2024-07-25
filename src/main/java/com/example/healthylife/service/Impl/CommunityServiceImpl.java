package com.example.healthylife.service.Impl;

import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.repository.CommunityRepository;
import com.example.healthylife.repository.UserRepository;
import com.example.healthylife.service.CommunityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    public CommunityServiceImpl(CommunityRepository communityRepository, UserRepository userRepository){

        this.communityRepository=communityRepository;


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
}
