package org.anonymous.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author child
 * 2019/4/17 10:07
 * 四大通知
 * 自定义事务管理器: 也就是当前切面类 (第四天是使用 spring 自带的事务管理器)
 */
@Component("aspect3") // 让 ioc 管理 切面对象
@Aspect //指定 当前类为 切面类
public class Aspect3 {

    /**
     * 抽取 execution() 表达式
     * 必须定义在一个 无参 无返回值 无实际方法内容的 方法上
     * 使用： 谁要使用，直接调用 方法名()
     * 必须 使用 符合版本的 aspectjweaver.jar (1.9.3 目前最新版可以)
     * 1.8.3 不支持, 会报运行时异常
     *
     * @Pointcut 标注的方法可以有参数： 在调用 pointcut 时，可以将参数 传递到 调用者
     * <p>
     * 例：
     * @Pointcut("com.xyz.myapp.SystemArchitecture.dataAccessOperation() && args(account,..)")
     * private void accountDataAccessOperation(Account account) {}
     * @Before("accountDataAccessOperation(account)") public void validateAccount(Account account) {
     * // ...
     * }
     */
    //    @Pointcut(value = "execution(* org.anonymous..UserServiceImpl.save(..))") //the pointcut expression
    @Pointcut("bean(*ServiceImpl)") //指定 ioc 中的 类
    private void pt() {
    } //the pointcut signature


    //注解 无法 指定执行顺序:
    //    @AfterReturning("execution(* org.anonymous..*.save(..))") //切入点 异常就不执行
    @AfterReturning(pointcut = "pt()")
    public void finally0() {
        System.out.println("afterReturning");
    }

    @AfterThrowing(value = "pt()", throwing = "ex") //切入点 异常时 执行: 可以指定 特定的异常类型(方法形参)
    public void afterException(Exception ex) {
        System.out.println("AfterThrowing");
    }

    @After("pt()") //无论如何都会执行: 一定在 @AfterThrowing/@AfterReturning 之前执行 //finally
    public void after() {
        System.out.println("after");
    }

    @Before("pt()") //无论如何都会第一个执行
    public void before() {
        System.out.println("before");
    }

}
