package com.example.healthylife.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 허용할 Origin 설정
        config.addAllowedOrigin("http://ec2-43-201-150-178.ap-northeast-2.compute.amazonaws.com:8081/");
        config.addAllowedOrigin("http://localhost:8081/swagger-ui/index.html");

        // 모든 HTTP 메서드 허용
        config.addAllowedMethod("*");

        // 모든 헤더 허용
        config.addAllowedHeader("*");

        // 자격 증명 허용
        config.setAllowCredentials(true);

        // 모든 경로에 대해 위의 CORS 설정 적용
        source.registerCorsConfiguration("/**", config);

        // CORS 필터 반환
        return new CorsFilter(source);
    }
}
