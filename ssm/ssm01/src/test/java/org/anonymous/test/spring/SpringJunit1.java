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
 * 2019/4/19 11:07
 * 半 xml 方式
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean-r3.xml") //bean1: 全 xml
public class SpringJunit1 {
    @Autowired
    @Qualifier("accountServiceImplR2")
    private AccountService accountService;

    @Test
    public void test1() {
        accountService.transfer();
    }
}
