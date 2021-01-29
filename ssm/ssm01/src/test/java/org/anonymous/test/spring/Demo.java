package org.anonymous.test.spring;

import org.anonymous.beans.Account;
import org.anonymous.config.SpringConfig;
import org.anonymous.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author child
 * 2019/4/14 16:42
 * junit 与 crud
 */
@RunWith(SpringJUnit4ClassRunner.class)
//指定 谁 加载配置文件/配置类: ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
//@ContextConfiguration(locations = "classpath:bean.xml") //指定配置文件: 数组
@ContextConfiguration(classes = {SpringConfig.class}) //指定 配置类: 数组
public class Demo {
    @Autowired //为对象 注入 ioc 容器相应的值
    private AccountService accountService;

    @Test //曾
    public void create() {
        Account account = new Account();
        account.setName("rose");
        account.setMoney(21324);
        accountService.save(account);
    }

    @Test //删
    public void delete() {
        int id = 6;
        accountService.delete(id);
    }

    @Test //改
    public void update() {
        Account account = new Account();
        account.setId(4);
        account.setName("white");
        accountService.update(account);
    }

    @Test //查
    public void retrieve() {
        List<Account> list = accountService.find();
        list.forEach(System.out::println);
    }
}

