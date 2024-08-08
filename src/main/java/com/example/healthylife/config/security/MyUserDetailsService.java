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
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // username을 기준으로 사용자 조회
        Optional<UserEntity> userEntityOptional = userService.findUserById(userId);
        if (userEntityOptional.isEmpty()) {
            throw new UsernameNotFoundException("해당하는 유저가 없습니다.");
        }

        UserEntity userEntity = userEntityOptional.get();
        return MyUserDetails.create(userEntity); // UserDetails 객체를 생성할 때 userEntity를 직접 전달
    }
}
