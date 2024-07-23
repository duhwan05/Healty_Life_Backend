package com.example.healthylife.config;

import com.example.healthylife.config.jwt.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Slf4j
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("Configuring HttpSecurity");

        http.csrf().disable();
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
            .antMatchers(
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/jwt/**",
                "/user/signup",
                "/v3/api-docs")
            .permitAll()
            .anyRequest().authenticated();

        CharacterEncodingFilter encFilter = new CharacterEncodingFilter();
        encFilter.setEncoding("UTF-8");
        encFilter.setForceEncoding(true);
        http.addFilterAfter(encFilter, SecurityContextPersistenceFilter.class);

        http.addFilterAfter(new JwtAuthenticationFilter(), SecurityContextPersistenceFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
