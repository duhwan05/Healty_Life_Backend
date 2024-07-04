package com.example.healthylife.config;

import com.example.healthylife.config.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

//@EnableWebSecurity
//@RequiredArgsConstructor
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {
//    public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

//    private final AuthenticationManager authenticationManager;
//
//    @Override
//    public void configure(HttpSecurity http) {
//        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(authenticationManager);
//        http.addFilterAfter(filter, LogoutFilter.class);
//    }
}