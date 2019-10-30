package com.example.demo.config;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/24 16:55
 */
public class MyReplace implements MethodReplacer {
    /**
     * Reimplement the given method.
     *
     * @param obj    the instance we're reimplementing the method for
     * @param method the method to reimplement
     * @param args   arguments to the method
     * @return return value for the method
     */
    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        System.out.println(String.format("obj: %s, method: %s, args: %s", obj, method, args));
        return null;
    }
}
