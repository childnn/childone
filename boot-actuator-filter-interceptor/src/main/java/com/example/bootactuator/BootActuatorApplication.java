package com.example.bootactuator;

import com.example.bootactuator.filters.demo2.Filter2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.beans.BeansEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import java.util.Map;

/**
 * http://localhost:9000/actuator
 *
 * @see ServletComponentScan 扫描 {@link WebFilter} {@link WebServlet} {@link WebListener}
 */
@ServletComponentScan(basePackageClasses = Filter2.class)
@RestController
@SpringBootApplication
public class BootActuatorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(BootActuatorApplication.class, args);
        BeansEndpoint endpoint = ctx.getBean(BeansEndpoint.class);
        BeansEndpoint.ApplicationBeans beans = endpoint.beans();
        Map<String, BeansEndpoint.ContextBeans> contexts = beans.getContexts();
       /* contexts.forEach((k, v) -> {
            System.err.println(k);
            Map<String, BeansEndpoint.BeanDescriptor> bs = v.getBeans();
            bs.forEach((k1, v1) -> System.err.println(k1 + ": " + v1.getType()));
        });*/
    }

    @RequestMapping
    public String hello() {
        return "hello";
    }

}
