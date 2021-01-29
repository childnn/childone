package org.anonymous.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author child
 * 2019/4/17 11:17
 * 环绕
 */
@Component("aspect2") //ioc
@Aspect //指定当前类为 切面类
public class Aspect2 {

    //环绕: 固定方法的执行顺序
    @Around("execution(* org.anonymous..UserServiceImpl.save(..))")
    public Object around(ProceedingJoinPoint pj) { // 通知方法的 第一个参数 必须是 ProceedingJoinPoint
        Object retVal = null;
        try {
            //前置通知
            System.out.println("之前..");
            //原方法
            retVal = pj.proceed();// invoke
            //后置通知
            System.out.println("之后..");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("异常。。");
        } finally {
            //最终通知
            System.out.println("最终...");
        }
        return retVal;
    }
}
