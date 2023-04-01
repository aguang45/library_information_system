package com.libraryinformation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor())
                .excludePathPatterns("/users/login") // 不拦截登录请求
                .excludePathPatterns("/users/register") // 不拦截注册请求
                .excludePathPatterns("/users/id/*") // 注册时验证用户名是否存在
                .excludePathPatterns("/checkCode") // 不拦截验证码请求
//                .excludePathPatterns("/**");
                .addPathPatterns("/**"); // 拦截其他所有请求
    }
}
