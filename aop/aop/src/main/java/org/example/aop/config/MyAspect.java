package org.example.aop.config;

import org.aspectj.lang.JoinPoint;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2024/6/4
 */
// @Aspect
public class MyAspect {

    // https://docs.spring.io/spring-framework/reference/core/aop/ataspectj/pointcuts.html


    // @After("org.example.aop.config.CommonPointcuts.pointcut()")
    public void after(JoinPoint joinPoint) {
        System.out.println("MyAspect.after");
    }

    // @Before("execution(public * *(..))")
    public void before(JoinPoint joinPoint) {
        System.out.println("MyAspect.before");
    }

    // @Before("bean(aopExpService)")
    public void beforeBean(JoinPoint joinPoint) {
        System.out.println("MyAspect.beforeBean");
    }

    // @Before("org.example.aop.config.CommonPointcuts.pointcutWithin()")
    public void within(JoinPoint joinPoint) {
        System.out.println("MyAspect.pointcutWithin");
    }


    public void hello() {
        System.out.println("Hello! Spring-AOP!");
    }

}
