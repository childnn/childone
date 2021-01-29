package com.example.bootactuator.servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/10/29 23:56
 */
@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<MyServlet> myServletServletRegistrationBean() {
        ServletRegistrationBean<MyServlet> myServlet = new ServletRegistrationBean<>();
        myServlet.setLoadOnStartup(1);
        myServlet.setServlet(myServlet());
        myServlet.addUrlMappings("/myServlet");
        //myServlet.
        return myServlet;
    }

    @Bean
    public MyServlet myServlet() {
        return new MyServlet();
    }

}
