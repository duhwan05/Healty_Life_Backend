package com.example.healthylife.config.security.jwt;

import com.example.healthylife.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogMessage;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

// AbstractAuthenticationProcessingFilter 이걸 상속받는건 특정 URL에 대한 인증 처리를 담당하는 필터로 사용된다는 것임.
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";

    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login",
            "POST");

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;

    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;

    private boolean postOnly = true;

    //JWT 생성 및 검증
    private JwtUtil jwtUtil = new JwtUtil("sklskljsklsjalkjklsjSKLSAKLJsklsklsjlksjsakljslkajsalksaksa",
            10 * 60 * 1000, 24 * 60 * 60 * 1000);

    public JwtAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // 요청 헤더에서 토큰을 추출  (header)"Authorization: Bearer {accessToken}"
        String jwtToken = jwtUtil.extractTokenFromHeader(request.getHeader("Authorization"));
        if (Objects.nonNull(jwtToken) && jwtUtil.validateToken(jwtToken)) {
            return jwtUtil.getAuthentication(jwtToken);
        }


        // login Form submit
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = obtainUsername(request);
        username = (username != null) ? username.trim() : "";
        String password = obtainPassword(request);
        password = (password != null) ? password : "";
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username,
                password);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        // access, refresh token -> response
        String accessToken = jwtUtil.createAccessToken(UserEntity.builder()
                .userId(authResult.getName())
                .build());
        String refreshToken = jwtUtil.createRefreshToken(UserEntity.builder()
                .userId(authResult.getName())
                .build());
        Map responseMap = Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        String responseJson = new ObjectMapper().writeValueAsString(responseMap);
        response.addHeader("accessToken", accessToken);
        response.addHeader("refreshToken", refreshToken);
        response.getWriter().write(responseJson);
    }




    /**
     * Enables subclasses to override the composition of the password, such as by
     * including additional values and a separator.
     * <p>
     * This might be used for example if a postcode/zipcode was required in addition to
     * the password. A delimiter such as a pipe (|) should be used to separate the
     * password and extended value(s). The <code>AuthenticationDao</code> will need to
     * generate the expected password in a corresponding manner.
     * </p>
     * @param request so that request attributes can be retrieved
     * @return the password that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */
    @Nullable
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(this.passwordParameter);
    }

    /**
     * Enables subclasses to override the composition of the username, such as by
     * including additional values and a separator.
     * @param request so that request attributes can be retrieved
     * @return the username that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */
    @Nullable
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(this.usernameParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     * set
     */
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the username from the login
     * request.
     * @param usernameParameter the parameter name. Defaults to "username".
     */
    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    /**
     * Sets the parameter name which will be used to obtain the password from the login
     * request..
     * @param passwordParameter the parameter name. Defaults to "password".
     */
    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to
     * true, and an authentication request is received which is not a POST request, an
     * exception will be raised immediately and authentication will not be attempted. The
     * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getUsernameParameter() {
        return this.usernameParameter;
    }

    public final String getPasswordParameter() {
        return this.passwordParameter;
    }
//    public class JwtAuthenticationFilter extends OncePerRequestFilter {
//    public static final String AUTHORIZATION_HEADER = "Authorization";
//    public static final String BEARER_PREFIX = "Bearer ";
//
//    private final AuthenticationManager authenticationManager;
//    private JwtUtil jwtUtil = new JwtUtil("dkljalkjdaslkjdadlkassadjdajdlkjdasljdlkdasjlkadsjabcdefg", 10 * 60 * 1000);
//
//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String jwt = resolveToken(request);
//
//        if (StringUtils.hasText(jwt)) {
//            try {
//                Authentication jwtAuthenticationToken = new JwtAuthenticationToken(jwt);
//                Authentication authentication = authenticationManager.authenticate(jwtAuthenticationToken);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } catch (AuthenticationException authenticationException) {
//                SecurityContextHolder.clearContext();
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private String resolveToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
}
/*
    //폼 데이터에서 사용자 이름을 가져올 때 사용할 키를 정의함
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    //폼 데이터에서 사용자 비밀번호를 가져올 때 사용할 키를 정의함
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    //  /login url에 post 요청이 들어올 때 필터가 작동하도록 설정
    private RequestMatcher requiresAuthenticationRequestMatcher = new AntPathRequestMatcher("/login", "POST");;

    //사용자 이름 파라미터 키 설정
    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    //사용자 비밀번호 파라미터 키 설정
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;


    //기본 url 패턴 설정하는 생성자. 필터가 적용될 url을 설정함

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 요청 헤더에서 토큰을 추출
        String token = jwtUtil.extractTokenFromHeader(((HttpServletRequest) request)
                .getHeader("Authorization"));

        //토큰 유효성 검증, 인증 정보 설정
        setDetails(token);

        //요청 전달
        chain.doFilter(request, response);

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!request.getMethod().equals("POST")) { //post 요청이 아닐 경우 예외처리
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //요청에서 사용자 이름과 비밀번호 가져오기
        String username = obtainUsername(request);
        username = (username != null) ? username.trim() : "";
        String password = obtainPassword(request);
        password = (password != null) ? password : "";
        //JWT토큰 생성
        String accessToken = jwtUtil.createAccessToken(UserEntity.builder()
                        .userId(username)
                        .userPw(password)
                .build());

        //생성된 토큰으로 인증 정보 설정
        Authentication authentication = setDetails(accessToken);

        //인증 정보 반환
        return authentication;
    }

    //요청 파라미터에서 사용자 이름과 비밀번호를 추출(헬퍼메소드)
    @Nullable
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(this.passwordParameter);
    }

    @Nullable
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(this.usernameParameter);
    }


    protected Authentication setDetails(String token) {
        if (token != null && jwtUtil.validateToken(token)) {
            // 유효성 검증 이후 토큰을 context에 저장
            Authentication authentication = jwtUtil.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication); //여기 SecurityContextHolder에 저장.
            return authentication;
        }
        return null;
    }

}
*/