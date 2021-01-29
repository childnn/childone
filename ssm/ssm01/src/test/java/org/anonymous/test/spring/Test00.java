package org.anonymous.test.spring;

import org.anonymous.beans.Animal;
import org.anonymous.beans.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author child
 * 2019/4/24 20:09
 * 参考: Test0 + bean0.xml
 * <p>
 * 本测试类用来测试 : setter-based di //结合 bean00.xml
 * console:
 * animal-singleton
 * user--多例对象!!!!  1
 * animal -- setter-based di
 * main----main
 * <p>
 * animal:true
 * ==============================
 * user--多例对象!!!!  2
 * user--多例对象!!!!  3
 * user:false
 */
public class Test00 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean00.xml");

        System.out.println("main----main\n");

        Animal animal = applicationContext.getBean(Animal.class);
        Animal animal1 = applicationContext.getBean(Animal.class);
        System.out.println(animal); //测试 user 属性是否赋值成功
        System.out.println("animal:" + (animal == animal1));

        System.out.println("==============================");

        User user = applicationContext.getBean(User.class);
        User user1 = applicationContext.getBean(User.class);
        System.out.println("user:" + (user == user1));

        //ioc 销毁： 单例对象销毁
        applicationContext.close(); //animal: destroy..
    }
}
