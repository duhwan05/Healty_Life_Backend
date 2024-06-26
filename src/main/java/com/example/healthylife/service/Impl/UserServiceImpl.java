package com.example.healthylife.service.Impl;

import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.repository.UserRepository;
import com.example.healthylife.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //회원 전체조회
    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> userList(){
        return (List<UserEntity>) userRepository.findAll();

    }

    @Override
    public Optional<UserEntity> findUserById(String userId) {
        return userRepository.findByUserId(userId);
    }

    //회원 등록
    @Override
    @Transactional
    public UserEntity registerUser(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

    //회원 수정
    @Override
    @Transactional
    public UserEntity updateUser(UserEntity userEntity){
        UserEntity user = userRepository.findById(userEntity.getUserSq()).get();

        UserEntity resultEntity = UserEntity.builder().build();

        if(Objects.nonNull(user.getUserName()) && !"".equalsIgnoreCase(user.getUserName())){
            resultEntity = userEntity.toBuilder().userName(user.getUserName()).build();
        }

        if (Objects.nonNull(user.getUserEmail())&& !"".equalsIgnoreCase(user.getUserEmail())){
            resultEntity = userEntity.toBuilder().userEmail(user.getUserStatus()).build();

        }
        return userRepository.save(resultEntity);
    }

    //회원 삭제
    @Override
    @Transactional
    public void deleteUserBySq(long userSq){
        userRepository.deleteById(userSq);
    }


}
