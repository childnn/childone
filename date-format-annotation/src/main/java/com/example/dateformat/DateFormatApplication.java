package com.example.dateformat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 根据当前环境的依赖推测应用环境类型
 *
 * @see org.springframework.boot.WebApplicationType#deduceFromClasspath()
 * @see org.springframework.boot.web.servlet.context.WebApplicationContextServletContextAwareProcessor
 * @see org.springframework.web.context.request.RequestScope
 * @see org.springframework.web.context.request.SessionScope
 */
@SpringBootApplication
public class DateFormatApplication {

    public static void main(String[] args) {
        SpringApplication.run(DateFormatApplication.class, args);
    }

}
