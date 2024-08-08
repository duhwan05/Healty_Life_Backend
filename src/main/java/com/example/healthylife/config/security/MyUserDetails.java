package com.example.healthylife.config.security;

import com.example.healthylife.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Getter
public class MyUserDetails implements UserDetails {

    private final UserEntity userEntity;
    private final Collection<? extends GrantedAuthority> authorities; // 사용자 권한 목록을 담은 GrantedAuthority의 컬렉션

    // 생성자 직접 작성, 필드 초기화
    public MyUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
        // 역할을 하드코딩으로 설정
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // 정적 팩토리 메소드. UserEntity를 인자로 받아 MyUserDetails 객체 생성
    public static UserDetails create(UserEntity userEntity) {
        return new MyUserDetails(userEntity);
    }

    @Override
    // 사용자 권한 목록 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    // 사용자 패스워드 반환
    public String getPassword() {
        return userEntity.getUserPw();
    }

    @Override
    // 사용자 ID 반환
    public String getUsername() {
        return userEntity.getUserId();
    }

    @Override
    // 사용자 계정 상태 나타냄. 모든 계정 활성화 상태로 설정.
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
