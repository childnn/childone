package jvm.bytecode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/15 10:57
 */
public class DynamicProxyTest {
    public static void main(String[] args) {
        // 代理对象只是接口的实现类，不是 Hello 本身。
        IHello hello = /*(IHello) */new DynamicProxy<IHello>().bind(new Hello());
        hello.sayHello();
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", true);
    }

    interface IHello {
        void sayHello();
    }

    static class Hello implements IHello {
        @Override
        public void sayHello() {
            System.out.println("hello world");
        }
    }

    static class DynamicProxy<T> implements InvocationHandler {

        private T originalObj;

        @SuppressWarnings("unchecked")
        T bind(T originalObj) {
            this.originalObj = originalObj;
            return (T) Proxy.newProxyInstance(originalObj.getClass().getClassLoader(),
                    originalObj.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("welcome");
            return method.invoke(originalObj, args);
        }
    }

}
