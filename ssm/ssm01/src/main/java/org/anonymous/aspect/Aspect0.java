package org.anonymous.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author child
 * 2019/4/17 14:35
 */
@Component("aspect0") //ioc
@Aspect //aop: 定义当前类为 切面类
public class Aspect0 {
    @Around("execution(* org.anonymous..UserServiceImpl.save())")
    public void around(ProceedingJoinPoint pj) {
        try {
            System.out.println("before..");
            pj.proceed();
            System.out.println("after-returning..");
        } catch (Throwable throwable) {
            System.out.println("after-throwing..");
            throwable.printStackTrace();
        } finally {
            System.out.println("after..");
        }

    }
}
