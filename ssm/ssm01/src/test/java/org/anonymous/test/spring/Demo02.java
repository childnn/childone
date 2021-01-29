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
 * 2019/4/17 19:34
 * aop: 环绕通知- org.anonymous.aspect.TransactionManager0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:bean1.xml")
public class Demo02 {

    @Autowired
    @Qualifier("accountServiceImpl0")
    private AccountService accountService;

    @Test
    public void test1() {
        accountService.transfer();
    }
}
