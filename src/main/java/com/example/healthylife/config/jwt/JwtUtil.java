package com.example.healthylife.config.jwt;

import com.example.healthylife.config.security.MyUserDetails;
import com.example.healthylife.entity.UserEntity;
import com.example.healthylife.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
//JWT 토큰의 생성, 검증, Claims 추출 등을 처리합니다.
public class JwtUtil {

    private final UserService userService;

    private final Key key; //JWT 시크릿 키
    private final long accessTokenExpTime;  //access토큰 만료시간
    private final long refreshTokenExpTime; //refresh토큰 만료시간

    //yml에 저장해둠
    public JwtUtil(@Value("${jwt.secret}") String secretKey, UserService userService,
                   @Value("${jwt.accessTokenExpTime}") long accessTokenExpTime,
                   @Value("${jwt.refreshTokenExpTime}") long refreshTokenExpTime) {
        this.userService = userService;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
        this.refreshTokenExpTime = refreshTokenExpTime;
    }

    // UserEntity를 바탕으로 AccessToken을 생성함.
    public String createAccessToken(UserEntity user) {
        return createToken(user, accessTokenExpTime);
    }

    public String createRefreshToken(UserEntity user) {
        return createToken(user, refreshTokenExpTime);
    }

    // JWT 생성
    private String createToken(UserEntity userEntity, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("userSq", userEntity.getUserSq());
        claims.put("userId", userEntity.getUserId());
        claims.put("userName", userEntity.getUserName());
        claims.put("role", "ROLE_USER");  // TODO: 역할을 동적으로 설정하도록 수정할 수 있음

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰을 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.warn("잘못된 JWT 토큰입니다.", e);
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.", e);
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            log.warn("JWT 클레임이 비어 있습니다.", e);
        }
        return false;
    }

    // Authorization 헤더에서 토큰 추출
    public String extractTokenFromHeader(String authorization) {
        if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer ")) {
            return null;
        }
        return authorization.substring(7).trim();
    }

    // JWT에서 Claims를 추출
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // 토큰에서 사용자 아이디 추출
    public String getUserId(String token) {
        return parseClaims(token).get("userId", String.class);
    }

    //토큰에서 사용자 고유번호 추출
    public Long getUserSq(String token) {
        return parseClaims(token).get("userSq", Long.class);
    }

    // Access Token에 들어있는 정보를 꺼내 Authentication 객체를 생성 후 반환한다.
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get("role") == null) {
            throw new InvalidTokenException("role이 존재하지 않습니다!!");
        }

        // JWT에서 userSq 추출
        String userId = claims.get("userId", String.class);

        // UserEntity를 userSq를 통해 조회
        UserEntity userEntity = userService.findUserById(userId)
                .orElseThrow(() -> new InvalidTokenException("유저 정보를 찾을 수 없습니다."));

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get("role").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // UserDetails 객체 생성
        UserDetails principal = MyUserDetails.create(userEntity);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }



}
