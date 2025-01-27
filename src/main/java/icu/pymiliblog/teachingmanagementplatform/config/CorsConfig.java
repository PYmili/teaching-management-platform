package icu.pymiliblog.teachingmanagementplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOriginPatterns(List.of("*")); // 使用通配符
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 允许所有请求类型
        corsConfiguration.setAllowedHeaders(List.of("*")); // 允许所有请求头
        corsConfiguration.setExposedHeaders(List.of("Authorization", "Content-Length", "Content-Type")); // 暴露特定的响应头

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // 应用到所有路径

        return new CorsFilter(source);
    }
}
