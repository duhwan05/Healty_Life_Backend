package com.example.healthylife.config.security;

import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor //final 필드 초기화 생성자 자동생성
public class MyUserDetailsService implements UserDetailsService {
    private final UserService userService;


    @Override
    //username으로 사용자 한 명 가져옴(UserId)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> userEntityOptional = userService.findUserById(username);
        if (userEntityOptional.isEmpty()) {
            throw new UsernameNotFoundException("해당하는 유저가 없습니다.");
        }

        UserEntity userEntity = userEntityOptional.get();

        return new MyUserDetails(userEntity);
    }
}
