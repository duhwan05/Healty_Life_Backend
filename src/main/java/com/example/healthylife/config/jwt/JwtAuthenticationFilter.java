package com.example.healthylife.config.jwt;

import io.micrometer.core.instrument.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;


import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// AbstractAuthenticationProcessingFilter 이걸 상속받는건 특정 URL에 대한 인증 처리를 담당하는 필터로 사용된다는 것임.
//역할: HTTP 요청의 JWT 인증 처리
@Slf4j
@RequiredArgsConstructor
public class    JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;

    //요청을 넘기는 역할을 하는 메소드
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        // Swagger UI와 같은 공개된 엔드포인트는 필터링하지 않음
        if (path.startsWith("/swagger-ui/") || path.startsWith("/v3/api-docs/") || path.startsWith("/swagger-resources/") || path.startsWith("/webjars/")) {
            chain.doFilter(request, response);
            return;
        }
        try {
            restoreAuthentication(httpRequest, (HttpServletResponse) response);
            chain.doFilter(request, response);
        } catch (AuthenticationException e) {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
        }
    }

    //헤더에서 JWT 토큰을 추츨하는 메소드
    private void restoreAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        String jwtToken = jwtUtil.extractTokenFromHeader(request.getHeader("Authorization"));

        if (StringUtils.isBlank(jwtToken)) {
            log.warn("토큰이 없습니다.");
            return;
        }

        if (jwtUtil.validateToken(jwtToken)) {
            Authentication authentication = jwtUtil.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            log.warn("유효하지 않은 토큰입니다.");
            throw new AuthenticationException("유효하지 않은 토큰입니다.") {};
        }
    }




}
