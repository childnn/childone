package org.anonymous.test.spring;

import org.anonymous.beans.Animal;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author child
 * 2019/4/25 11:35
 * 测试 depends-on 对 对象销毁顺序的影响: 单例对象销毁, 其依赖的多例对象并不会销毁
 * user 多例对象, animal 单例对象, account 单例对象
 * account 依赖 animal, animal 依赖 user
 */
public class Test_depends_on {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean_depends-on.xml");
        System.out.println("main-----main");
        Animal animal = applicationContext.getBean(Animal.class);
        System.out.println(animal);
        applicationContext.close();
    }
}
