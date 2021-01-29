package org.anonymous.test;

import org.anonymous.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author child
 * 2019/4/13 22:23
 * 简单属性的注入方式二: set 属性方式
 */
public class Demo01 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean1.xml");
        AccountService accountService = (AccountService) applicationContext.getBean("accountService");
        accountService.save();
    }
}
