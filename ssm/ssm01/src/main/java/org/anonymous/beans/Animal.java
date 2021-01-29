package org.anonymous.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author child
 * 2019/4/24 18:46
 * 设置单例对象: 依赖与多例对象: user
 */
public class Animal {

    private static int i = 1;
    @Autowired
    @Qualifier("user")
    private User user;

    public Animal() {
        System.out.println("animal-singleton:set:" + i++);
    }

    private Animal(User user) {
        System.out.println("aninmiinininimal--constructor-based di:" + i++);
    }

    public void setUser(User user) {
        System.out.println("animal -- setter-based di");
        this.user = user;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "user=" + user +
                '}';
    }

    private void destroy() {
        System.out.println("animal: destroy..");
    }
}
