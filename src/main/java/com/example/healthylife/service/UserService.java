package com.example.healthylife.service;

import com.example.healthylife.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    //회원 전체조회
    List<UserEntity> userList();
    //회원 등록
    UserEntity registerUser(UserEntity userEntity);
    //회원 수정
    UserEntity updateUser(UserEntity userEntity);
    //회원 삭제
    void deleteUserBySq(long userSq);
}
