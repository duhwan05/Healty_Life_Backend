//package com.example.healthylife.config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOriginPattern("http://localhost:8081/swagger-ui/index.html");
//        config.addAllowedOriginPattern("https://trendy-healthy-backend.store/swagger-ui/");
//        config.addAllowedOriginPattern("https://trendy-healthy.store/");
//        config.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
//        config.addAllowedHeader("*"); // 모든 헤더 허용
//        config.setAllowCredentials(true); // 자격 증명 허용
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//}
