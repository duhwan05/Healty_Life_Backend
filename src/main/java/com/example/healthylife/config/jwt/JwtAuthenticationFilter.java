package com.example.healthylife.config.jwt;

import io.micrometer.core.instrument.util.StringUtils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;


import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

// AbstractAuthenticationProcessingFilter 이걸 상속받는건 특정 URL에 대한 인증 처리를 담당하는 필터로 사용된다는 것임.

    public class JwtAuthenticationFilter extends GenericFilterBean {

        //JWT 생성 및 검증
        private JwtUtil jwtUtil = new JwtUtil("sklskljsklsjalkjklsjSKLSAKLJsklsklsjlksjsakljslkajsalksaksa",
                10 * 60 * 1000, 24 * 60 * 60 * 1000);

        public JwtAuthenticationFilter() {
            super();
        }


            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                restoreAuthentication((HttpServletRequest) request, (HttpServletResponse) response);
                chain.doFilter(request, response);
            }



                private void restoreAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
                    // 요청 헤더에서 토큰을 추출  (header)"Authorization: Bearer {accessToken}"
                    String jwtToken = jwtUtil.extractTokenFromHeader(request.getHeader("Authorization"));


                        if (StringUtils.isBlank(jwtToken)) {
                            return;
                        }


                    Authentication authentication = jwtUtil.getAuthentication(jwtToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);


    }




}
