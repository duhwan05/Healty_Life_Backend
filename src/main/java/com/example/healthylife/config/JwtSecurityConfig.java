package com.example.healthylife.config;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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