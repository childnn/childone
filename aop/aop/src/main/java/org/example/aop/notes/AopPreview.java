package org.example.aop.notes;

// import org.springframework.aop.MethodMatcher;

import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.ConstructorInvocation;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/4/15 9:56
 * 参见 spring-源码项目
 * spring-aop
 * @see org.springframework.aop.Pointcut central interface
 *      {@link org.springframework.aop.ClassFilter} filter the target classes
 *      {@link org.springframework.aop.MethodMatcher#matches}  target method of target class
 *      {@link org.springframework.aop.MethodMatcher#isRuntime()} static-method return false
 *  if possible, try to make pointcuts static, allowing the AOP framework to cache the results of
 *  pointcut evaluation when an AOP proxy is created.
 * @see org.aopalliance.intercept.MethodInvocation
 * @see org.aopalliance.intercept.MethodInterceptor
 * --@see org.springframework.cglib.proxy.MethodInterceptor
 * @see org.aopalliance.aop.Advice
 * @see org.springframework.aop.ThrowsAdvice
 * @see org.springframework.aop.BeforeAdvice
 * @see org.springframework.aop.AfterReturningAdvice
 */
public class AopPreview {
}

class DebuggingInterceptor implements MethodInterceptor,
        ConstructorInterceptor {

    public Object invoke(MethodInvocation i) throws Throwable {
        debug(i.getMethod(), i.getThis(), i.getArguments());
        return i.proceed();
    }

    public Object construct(ConstructorInvocation i) throws Throwable {
        debug(i.getConstructor(), i.getThis(), i.getArguments());
        return i.proceed();
    }

    public void debug(AccessibleObject ao, Object thsi, Object value) {
       // ...
    }

}