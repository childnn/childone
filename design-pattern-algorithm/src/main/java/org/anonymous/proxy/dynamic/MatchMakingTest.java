package org.anonymous.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/11 14:19
 * 保护代理.
 */
public class MatchMakingTest {
    public static void main(String[] args) {

    }

    public PersonBean getOwnerProxy(PersonBean person) {
        Class<? extends PersonBean> klass = person.getClass();
        return (PersonBean) Proxy.newProxyInstance(klass.getClassLoader(), klass.getInterfaces(), new OwnerInvocationHandler(person));
    }
    public PersonBean getNotOwnerProxy(PersonBean person) {
        Class<? extends PersonBean> klass = person.getClass();
        return (PersonBean) Proxy.newProxyInstance(klass.getClassLoader(), klass.getInterfaces(), new NotOwnerInvocationHandler(person));
    }

    public PersonBean getProxy(PersonBean person, InvocationHandler handler) {
        Class<? extends PersonBean> klass = person.getClass();
        return (PersonBean) Proxy.newProxyInstance(klass.getClassLoader(), klass.getInterfaces(), handler);
    }

}
