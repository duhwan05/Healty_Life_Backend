package com.example.healthylife.config.security;

import com.example.healthylife.config.jwt.JwtAuthenticationFilter;
import com.example.healthylife.config.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtUtil jwtUtil;
    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().configurationSource(corsConfigurationSource()) // CORS 설정 추가
                .and()
                .authorizeRequests()
                .antMatchers("/user/one","/user/delete","/user/update").authenticated()
                .antMatchers("/community/register", "/community/update", "/community/delete/**","/community/recommend/**","/community/myCommunityContents").authenticated()
                .antMatchers("/communityComments/insert", "/communityComments/update", "/communityComments/delete/**").authenticated()
                .antMatchers("/today/create","/today/myTodayContents","/today/update/**","/today/delete/**","/today/todayDetail/**").authenticated()
                .antMatchers("/todayComments/register", "/todayComments/update", "/todayComments/delete/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:8081", "https://trendy-healthy-backend.store", "https://trendy-healthy.store")); // 원본 설정
        configuration.setAllowedMethods(Arrays.asList("*")); // 모든 메서드 허용
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 자격 증명 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    //AuthenticationManager를 빈으로 등록하게 해서 시큐리티가 인증 매니저를 할수 있게 한다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // AuthenticationManager를 빈으로 등록하여 인증 관련 작업을 수행할 수 있도록 설정
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // DaoAuthenticationProvider를 설정하여 사용자 세부 정보와 비밀번호 암호화 설정을 Spring Security에 통합
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService); // 사용자 세부 정보를 제공하는 서비스 설정
        authProvider.setPasswordEncoder(passwordEncoder); // 비밀번호 암호화 설정
        return authProvider;
    }


}
