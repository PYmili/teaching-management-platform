package icu.pymiliblog.teachingmanagementplatform.config;

import icu.pymiliblog.teachingmanagementplatform.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web设置
 * @author PYmili
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 身份验证拦截器
    private final AuthenticationInterceptor authenticationInterceptor;

    public WebConfig(AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
    }

    /**
     * 重写添加拦截器
     * @param registry {@link InterceptorRegistry}
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/user/reg")
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/jwt/verify")
                .excludePathPatterns("/api/notice/**");
    }

}
