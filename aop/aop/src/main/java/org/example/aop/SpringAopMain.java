package org.example.aop;

import org.example.aop.config.AopConfig;
import org.example.aop.config.MyAspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2024/6/4
 */
public class SpringAopMain {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);
        context.getBean(MyAspect.class).hello();
    }

}
