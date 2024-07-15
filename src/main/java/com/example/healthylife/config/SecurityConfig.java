package com.example.healthylife.config;

import com.example.healthylife.config.jwt.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;



@Slf4j
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        log.debug("Using default configure(HttpSecurity). "
                + "If subclassed this will potentially override subclass configure(HttpSecurity).");
        http.csrf().disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests(request ->
                request.antMatchers(
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/jwt/**",
                                "/user/signup",
                                "/v3/api-docs")
                        .permitAll());
        http.authorizeRequests(request -> request.anyRequest().authenticated());
//        http.formLogin();//.loginPage("/api/login");
//        http.mvcMatcher("/api/**");
//        http.httpBasic();

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        http.addFilterAfter(filter, SecurityContextPersistenceFilter.class);
    }


        @Bean
        public PasswordEncoder passwordEncoder() {
            //db 데이터(패스워드)가 평문으로 돼 있어서 평문 인코더 사용
            return NoOpPasswordEncoder.getInstance();
        }



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//        // TODO token을 사용하는 방식이기 때문에 csrf를 disable합니다.
//                .csrf()//.disable()
//        ;
    /*
      --DisableEncodeUrlFilter
  --WebAsyncManagerIntegrationFilter
  SecurityContextPersistenceFilter
  HeaderWriterFilter
  CsrfFilter
  LogoutFilter
  UsernamePasswordAuthenticationFilter
  DefaultLoginPageGeneratingFilter
  DefaultLogoutPageGeneratingFilter
  --BasicAuthenticationFilter
  RequestCacheAwareFilter
  SecurityContextHolderAwareRequestFilter
  AnonymousAuthenticationFilter
  SessionManagementFilter
  ExceptionTranslationFilter
  FilterSecurityInterceptor
]
     */
//
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)
//
//                // enable h2-console
//                .and()
//                .headers()
//                .frameOptions()
//                .sameOrigin()
//
//                // 세션을 사용하지 않기 때문에 STATELESS로 설정
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//                .and()
//                .authorizeHttpRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다.
//                .requestMatchers("/api/authenticate").permitAll() // 로그인 api
//                .requestMatchers("/api/signup").permitAll() // 회원가입 api
//                .requestMatchers(PathRequest.toH2Console()).permitAll()// h2-console, favicon.ico 요청 인증 무시
//                .requestMatchers("/favicon.ico").permitAll()
//                .anyRequest().authenticated() // 그 외 인증 없이 접근X
//
//                .and()
//                .apply(new JwtSecurityConfig(tokenProvider)); // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig class 적용

//        return httpSecurity.build();
//    }


//    private final String ROLE_ADMIN = "ADMIN";
//    private final String ROLE_NORMAL = "NORMAL";
//
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//
//    public SecurityConfig(
//            AuthenticationManagerBuilder authenticationManagerBuilder
//    ) {
//        this.authenticationManagerBuilder = authenticationManagerBuilder;
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
    // REST API 방식이므로 CSRF 보안 토큰 생성 기능 종료
//                .csrf().disable()
//                // 요청 별 인증 필요 여부 혹은 권한 확인
//                .authorizeRequests()
//                // /auth 로 시작하는 모든 경로는 권한 확인 없이 수행 가능합니다.
//                .antMatchers("/auth/**").permitAll()
//                // 나머지는 인증 확인 및 역할 확인
//                .anyRequest()
//                .hasAnyRole(ROLE_ADMIN, ROLE_NORMAL)
//                // h2-console 사용을 위한 설정
//                .and()
//                .headers()
//                .frameOptions()
//                .sameOrigin()
//                // 세션을 사용하지 않도록 변경
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                // JWT 토큰 인증 필터 설정
//                .and()
//                .apply(new JwtSecurityConfig(authenticationManagerBuilder.getOrBuild()));
//    }
}