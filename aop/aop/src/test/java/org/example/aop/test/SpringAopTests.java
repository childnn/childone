package org.example.aop.test;

import org.example.aop.bean.Pojo;
import org.example.aop.bean.SimplePojo;
import org.example.aop.config.AopConfig;
import org.example.aop.service.AopExpService;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2024/6/4
 */
public class SpringAopTests {

    @Test
    void txTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);
        String s = context.getBean(AopExpService.class).txTest();
        System.out.println("s = " + s);
    }

    @Test
    void configurable() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);
        new SimplePojo().bar();
    }

    @Test
    void aop() {
        ProxyFactory factory = new ProxyFactory(new SimplePojo());
        factory.addInterface(Pojo.class);
        factory.addAdvice(new RetryAdvice());
        factory.setExposeProxy(true);

        Pojo pojo = (Pojo) factory.getProxy();
        // this is a method call on the proxy!
        pojo.foo();
    }

}
