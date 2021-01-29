package org.anonymous.test;

import org.anonymous.config.SpringConfig;
import org.anonymous.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author child
 * 2019/4/17 14:29
 * 纯注解: 配置类代替 配置文件的
 * 包扫描,
 * 开启 aop 注解支持
 */
public class Demo07 {

    @Test
    public void test1() {
        //初始化 ioc 容器: 对应 xml 方式的 ClassPathXmlApplicationContext
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService user = (UserService) applicationContext.getBean("user");
        user.save();
    }

    @Test //方式二: 创建对象之后, 再注册 配置类
    public void test2() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(SpringConfig.class);
        applicationContext.refresh(); //必须刷新, 否则会报异常

        UserService userService = applicationContext.getBean(UserService.class);
        userService.save();
    }

    @Test //方式三: scan : 等价于 @ComponentScan
    public void test3() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.scan("org.anonymous");
        applicationContext.refresh();

        UserService bean = applicationContext.getBean(UserService.class);
        bean.save();
    }

}
