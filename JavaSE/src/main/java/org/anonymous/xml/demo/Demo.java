package org.anonymous.xml.demo;

import org.anonymous.xml.domain.User;
import org.anonymous.xml.factory.BeanFactory;

/**
 * shadow
 * 2019/3/19 16:21
 * 接口+工厂类+配置文件: 解耦
 */
public class Demo {
    public void test1() throws Exception {
        User user = (User) BeanFactory.getObject("User");
        user.save();
    }
}
