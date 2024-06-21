package com.example.healthylife.serviceImpl;

import com.example.healthylife.entity.CommunityEntity;
import com.example.healthylife.repository.CommunityRepository;
import com.example.healthylife.service.CommunityService;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    public CommunityServiceImpl(CommunityRepository communityRepository){

        this.communityRepository=communityRepository;

    }

    //글전체조회
    @Override
    public List<CommunityEntity> communityList() {
        return communityRepository.findAll();
    }


    @Override
    public CommunityEntity registerCommunity(CommunityEntity communityEntity) {
        return communityRepository.save(communityEntity);
    }

    @Override
    public CommunityEntity updateCommunity(CommunityEntity communityEntity) {

        return communityRepository.save(communityEntity);
    }

    @Override
    public void deleteBySq(long communitySq) {

        communityRepository.deleteById(communitySq);
    }
}
