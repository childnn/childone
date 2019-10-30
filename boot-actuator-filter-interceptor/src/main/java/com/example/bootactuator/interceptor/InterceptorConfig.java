package com.example.bootactuator.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/8 18:05
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    // 添加自定义拦截器.
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor()).addPathPatterns("/*").order(2);
    }

    @Bean
    public HandlerInterceptor interceptor() {
        return new MyInterceptor();
    }
}
