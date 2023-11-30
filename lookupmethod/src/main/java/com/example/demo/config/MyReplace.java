package com.example.demo.config;

import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/24 16:55
 * @see org.springframework.beans.factory.support.LookupOverride
 * @see org.springframework.beans.factory.support.ReplaceOverride
 * @see org.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy.CglibSubclassCreator
 * @see org.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy.LookupOverrideMethodInterceptor
 * @see org.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy.ReplaceOverrideMethodInterceptor
 */
@Component
public class MyReplace implements MethodReplacer {


    /**
     * https://docs.spring.io/spring-framework/docs/6.0.8/reference/html/core.html#spring-core
     * 官方只提供了 bean.xml 配置 replace-method, 如何不用配置文件?
     *
     * 目前好像没有和 {@link org.springframework.beans.factory.annotation.Lookup} 类似的注解支持 replace-method
     *
     * Reimplement the given method.
     *
     * @param obj    the instance we're reimplementing the method for
     * @param method the method to reimplement
     * @param args   arguments to the method
     * @return return value for the method
     */
    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        System.out.printf("obj: %s, method: %s, args: %s%n", obj, method, args);
        System.out.println("我是replace代码: " + Arrays.asList(args));
        return "xxx";
    }

}
