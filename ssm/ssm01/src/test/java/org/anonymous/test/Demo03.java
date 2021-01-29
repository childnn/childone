package org.anonymous.test;

import org.anonymous.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author child
 * 2019/4/13 22:47
 * 依赖注入: 复杂属性 赋值 -- map
 * set 方式
 */
public class Demo03 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean3.xml");
        AccountService bean = (AccountService) applicationContext.getBean("bbb");
        bean.save();
    }
}
