package org.anonymous.test.spring;

import org.anonymous.beans.Animal;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author child
 * 2019/4/25 11:04
 * 自动注入 autowire 属性的测试:
 * 属性值:
 * no: default
 * constructor: 等价于 construtor-arg 标签 或 c:_0-ref
 * byName : 根据属性名引用 (成员变量名)
 * byType: 根据 Class 类型引用
 */
public class TestAutowiring {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean_autowiring.xml");
        System.out.println("main----main");
        Animal animal = applicationContext.getBean(Animal.class);
        System.out.println(animal);

    }
}
