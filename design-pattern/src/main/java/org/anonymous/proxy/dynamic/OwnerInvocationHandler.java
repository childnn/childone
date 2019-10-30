package org.anonymous.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/11 14:11
 * {@link InvocationHandler} 本身不是 proxy, 它只是一个帮助 proxy 的类,
 * proxy 会把调用转发给它处理. Proxy 本身是利用静态的 Proxy.newProxyInstance()
 * 方法在运行时动态创建的.
 */
public class OwnerInvocationHandler implements InvocationHandler {
    private PersonBean person;

    public OwnerInvocationHandler(PersonBean person) {
        this.person = person;
    }

    // 每次 proxy 的方法被调用, 就会导致 proxy 调用此方法.
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (methodName.startsWith("get")) {
            return method.invoke(person, args);
        } else if (methodName.equals("setHotOrNotRating")) { // 不允许调用此方法. -- 自己不能设置自己的分数.
            throw new IllegalAccessException();
        } else if (methodName.startsWith("set")) {
            return method.invoke(person, args);
        }
        // else
        return null;
    }
}
