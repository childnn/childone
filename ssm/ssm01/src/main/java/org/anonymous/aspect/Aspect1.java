package org.anonymous.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author child
 * 2019/4/16 21:57
 * 切面类： 存放通知（增强方法）的类
 */
public class Aspect1 {
    public void before() { //前置
        System.out.println("before");
    }

    public void after() { //最终
        System.out.println("after");
    }

    public void afterException() {//异常
        System.out.println("exception");
    }

    public void afterReturning() { //后置
        System.out.println("after-returning");
    }

    //环绕: 固定方法的执行顺序
    public void around(ProceedingJoinPoint pj) {
        try {
            //前置通知
            System.out.println("之前..");
            //原方法
            pj.proceed();  // invoke
            //后置通知
            System.out.println("之后..");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            //最终通知
            System.out.println("最终...");
        }
    }
}
