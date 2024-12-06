package com.lyc.member.config;

import com.lyc.common.interceptor.LogInterceptor;
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
    private LogInterceptor logInterceptor;

    @Resource
    private LoginMemberInterceptor loginMemberInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);
        registry.addInterceptor(loginMemberInterceptor)
                .addPathPatterns(
                        "/**"
                )
                .excludePathPatterns(
                        "/member/login",
                        "/member/member/hello",
                        "/member/member/send-code"
                );
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
