package org.anonymous.test;

import org.anonymous.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author child
 * 2019/4/13 23:01
 * 依赖注入: 对象属性的赋值
 */
public class Demo04 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean4.xml");
        AccountService bean = (AccountService) applicationContext.getBean("aaa");
        bean.save();
    }
}
