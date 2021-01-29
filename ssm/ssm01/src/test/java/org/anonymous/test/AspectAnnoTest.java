package org.anonymous.test;

import org.anonymous.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author child
 * 2019/4/17 10:05
 */
public class AspectAnnoTest {

    @Test//四大通知与注解: 切面类 Aspect1
    public void test1() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("aspect-annotation.xml");
        UserService userServiceImpl = (UserService) applicationContext.getBean("userServiceImpl");
        userServiceImpl.save();
    }

    @Test //环绕: 切面类 Aspect0
    public void test2() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("aspect-annotation.xml");
        UserService userServiceImpl = (UserService) applicationContext.getBean("userServiceImpl");
        userServiceImpl.save();
    }
}
