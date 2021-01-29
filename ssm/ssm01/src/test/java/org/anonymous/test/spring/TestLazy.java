package org.anonymous.test.spring;

import org.anonymous.beans.Animal;
import org.anonymous.beans.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author child
 * 2019/4/25 10:30
 * 单例对象的懒加载： lazy-initialized beans
 * A lazy-initialized bean tells the IoC container to create a bean instance when it is first requested, rather than at startup.
 */
public class TestLazy {

    public static void main(String[] args) {
        //第一句话执行完毕： ioc 创建完成： 但是没有对象注入
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean_lazy.xml");
        //        System.out.println(applicationContext);
        System.out.println("main----main...");
        /**
         * 为单例对象 animal 定义了懒加载： 在使用的时候创建
         */
        Animal animal = applicationContext.getBean("animal", Animal.class);
        Animal animal1 = applicationContext.getBean("animal", Animal.class);
        System.out.println(animal); //animal 的 user 属性没有被赋值
        System.out.println((animal == animal1) + "\n");

        User user = applicationContext.getBean("user", User.class);
        User user1 = applicationContext.getBean("user", User.class);
        System.out.println(user);
        System.out.println(user == user1);

    }
}
