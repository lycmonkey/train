package com.lyc.member.config;

import com.lyc.common.interceptor.LoginMemberInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lyc
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Resource
    private LoginMemberInterceptor loginMemberInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginMemberInterceptor)
                .addPathPatterns(
                        "/**"
                )
                .excludePathPatterns(
                        "/member/login",
                        "/member/member/hello",
                        "/member/member/send-code"
                ).order(0);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
