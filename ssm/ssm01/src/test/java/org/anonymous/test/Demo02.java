package org.anonymous.test;

import org.anonymous.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author child
 * 2019/4/13 22:32
 * 依赖注入: 复杂属性赋值: 数组/List/Set
 * 方式一: 构造器方式
 */
public class Demo02 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean2.xml");
        AccountService bean = (AccountService) applicationContext.getBean("aaa");
        bean.save();
    }
}
