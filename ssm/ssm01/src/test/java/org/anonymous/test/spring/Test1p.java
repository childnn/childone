package org.anonymous.test.spring;

import org.anonymous.beans.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author child
 * 2019/4/24 21:08
 * 参见 Test1
 */
public class Test1p {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean00.xml", "bean0.xml");
        System.out.println("main--main\n");

        User user = applicationContext.getBean(User.class);

    }
}
