package org.anonymous.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/11 14:11
 */
public class NotOwnerInvocationHandler implements InvocationHandler {
    private PersonBean person;

    public NotOwnerInvocationHandler(PersonBean person) {
        this.person = person;
    }

    // 每次 proxy 的方法被调用, 就会导致 proxy 调用此方法.
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (methodName.startsWith("get")) {
            return method.invoke(person, args);
        } else if (methodName.equals("setHotOrNotRating")) {
            return method.invoke(person, args);
        } else if (methodName.startsWith("set")) { // 不允许调用其他 setter 方法.
            throw new IllegalAccessException();
        }
        // else
        return null;
    }
}
