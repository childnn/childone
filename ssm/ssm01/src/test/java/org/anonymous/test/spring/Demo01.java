package org.anonymous.test.spring;

import org.anonymous.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author child
 * 2019/4/17 18:44
 * aop: 四大通知 - org.anonymous.aspect.TransactionManager
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:bean.xml")
public class Demo01 {
    @Autowired
    @Qualifier("accountServiceImpl0") //
    private AccountService accountService;

    @Test // aop 与 事务 : 切面类 + 四大通知
    public void test1() {
        accountService.transfer();
    }
}
