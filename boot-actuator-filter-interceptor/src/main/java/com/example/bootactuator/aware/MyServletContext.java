package com.example.bootactuator.aware;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see ServletContainerInitializer
 * @see ServletContextInitializer
 * @see org.springframework.web.SpringServletContainerInitializer
 * @see org.springframework.web.WebApplicationInitializer
 * @since 2020/10/29 23:28
 */
@Component
public class MyServletContext implements ServletContextAware,
        ServletContextInitializer,
        ServletContainerInitializer/*, WebApplicationInitializer*/ {

    @Override
    public void setServletContext(ServletContext servletContext) {
        //servletContext.setInitParameter("name", "jack"); // Initialization parameters cannot be set after the context has been initialized
        servletContext.setAttribute("async-supported", true);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("sfsdfsdfsdfsdf");
        servletContext.setInitParameter("name", "jack"); // 不可行
    }

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {

    }
}
