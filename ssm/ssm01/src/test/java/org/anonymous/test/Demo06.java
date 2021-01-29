package org.anonymous.test;

import org.anonymous.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author child
 * 2019/4/14 9:48
 * 一: 回顾 基本的依赖注入: Spring ioc 管理的对象属性
 * 二: 依赖注入 第三方 jar: druid, jdbcTemplate
 * 细节: 不仅 自己编写的 资源(Java类) 可以交给 spring ioc 管理, 第三方的资源也可以交给 spring 容器管理
 * 作业: crud : test-service-dao-jdbcTemplate-druid->database
 */
public class Demo06 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
        AccountService accountService = (AccountService) applicationContext.getBean("accountService");
        //删除
        accountService.delete(2);
    }
}
