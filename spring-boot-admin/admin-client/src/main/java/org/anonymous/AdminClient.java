package org.anonymous;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/5 13:26
 */
@SpringBootApplication
public class AdminClient implements ServletContextAware {
    public static void main(String[] args) {
        SpringApplication.run(AdminClient.class, args);
    }

    @Override
    public void setServletContext(ServletContext ctx) {
        String contextPath = ctx.getContextPath();
        System.out.println("contextPath = " + contextPath);
    }

}
