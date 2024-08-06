package com.example.healthylife.service;

import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.repository.CommunityRepository;
import com.example.healthylife.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
@RequiredArgsConstructor
@Service
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;



    //글전체조회
    public List<CommunityEntity> communityList() {
        return communityRepository.findAll();
    }


    //커뮤니티 글 작성
    public CommunityEntity registerCommunity(CommunityEntity communityEntity) {
        return communityRepository.save(communityEntity);
    }


    //커뮤니티 글 수정
    @Transactional
    public CommunityEntity updateCommunity(CommunityEntity communityEntity) {

        return communityRepository.save(communityEntity);
    }

    //커뮤니티 글 삭제
    public void deleteBySq(long communitySq) {

        communityRepository.deleteById(communitySq);
    }

    public List<CommunityEntity> findMyContents(String userId) {
        return communityRepository.findByUserUserId(userId);
    }
}
    //커뮤니티 내가 쓴 글 조회
//    @Override
//    public List<CommunityEntity> findMyContents(long userSq) {
//        return communityRepository.findByUserUserId(userSq);
//    }






