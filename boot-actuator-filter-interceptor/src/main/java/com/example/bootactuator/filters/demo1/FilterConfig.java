package com.example.bootactuator.filters.demo1;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/7 14:57
 */
@Configuration
public class FilterConfig {

    // 使用 FilterRegistrationBean 注入 filter
    @Bean
    public FilterRegistrationBean<Filter1> filterRegistrationBean() {
        FilterRegistrationBean<Filter1> registrationBean = new FilterRegistrationBean<>(filter1());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        registrationBean.setAsyncSupported(true);
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.INCLUDE,
                // Async support must be enabled on a servlet and for all filters involved in async request processing.
                DispatcherType.ASYNC);
        return registrationBean;
    }

    @Bean
    public Filter1 filter1() {
        return new Filter1();
    }
}
