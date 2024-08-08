package com.example.healthylife.service;

import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // userId를 기준으로 사용자 조회
    public Optional<UserEntity> findUserById(String userId) {
        return userRepository.findByUserId(userId);
    }

    //회원 등록
    @Transactional
    public UserEntity signUpUser(UserEntity userEntity) {
        String encodedPassword = passwordEncoder.encode(userEntity.getUserPw());
        userEntity = userEntity.withUserPw(encodedPassword);
        return userRepository.save(userEntity);
    }

    // 회원 수정
    @Transactional
    public UserEntity updateUser(UserEntity userEntity) {
        Long userSq = userEntity.getUserSq();
        UserEntity existingUser = userRepository.findById(userSq)
                .orElseThrow(() -> new RuntimeException("유저를 찾을수 없습니다."));
        
        UserEntity.UserEntityBuilder resultEntityBuilder = existingUser.toBuilder();
        //userDetail 변경하기
        if (Objects.nonNull(userEntity.getUserName()) && !"".equalsIgnoreCase(userEntity.getUserName())) {
            resultEntityBuilder.userName(userEntity.getUserName());
        }
        if (Objects.nonNull(userEntity.getUserEmail()) && !"".equalsIgnoreCase(userEntity.getUserEmail())) {
            resultEntityBuilder.userEmail(userEntity.getUserEmail());
        }
        if (Objects.nonNull(userEntity.getUserPw()) && !"".equalsIgnoreCase(userEntity.getUserPw())) {
            resultEntityBuilder.userPw(passwordEncoder.encode(userEntity.getUserPw()));
        }
        // 수정된 사용자 정보 저장
        UserEntity updatedUser = resultEntityBuilder.build();
        return userRepository.save(updatedUser);
    }

    // 회원 삭제
    @Transactional
    public void deleteUserBySq(Long userSq) {
        userRepository.deleteById(userSq);
    }
}
