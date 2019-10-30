package com.example.email.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/8/22 11:29
 */
@Configuration
public class MyCacheConfig {

    @Bean
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            /**
             *
             * @param target 目标类
             * @param method 目标方法
             * @param params 方法参数
             * @return
             */
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return method.getName() + "[" + Arrays.asList(params) + "]";
            }
        };
    }
}
