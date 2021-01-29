package org.anonymous.test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.anonymous.service.impl.UserServiceImpl;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author child
 * 2019/4/16 20:29
 * 不依赖接口的动态代理： cglib
 */
public class CglibTest {
    @Test
    public void test1() {
        final UserServiceImpl user = new UserServiceImpl();
        UserServiceImpl user1 = (UserServiceImpl) Enhancer.create(user.getClass(), new MethodInterceptor() {
            /**
             * @param o           代理对象的引用
             * @param method      被增强的方法
             * @param objects     被增强的方法运行过程中需要的参数
             * @param methodProxy 方法对象的代理对象
             * @return
             * @throws Throwable
             */
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) {
                try {
                    System.out.println("之前增强。。。");
                    Object invoke = method.invoke(user, objects);
                    System.out.println("之后增强。。。");
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("最终");
                }

                return null;
            }
        });
        user1.f();
        System.out.println("====================");
        System.out.println(user1);
    }
}
