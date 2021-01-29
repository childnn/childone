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
 * 2019/4/19 9:55
 * 全 xml 方式
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean-r4.xml") //半 xml
public class SpringJunit2 {
    @Autowired
    @Qualifier("accountServiceImplR1")
    private AccountService accountService;

    @Test
    public void test1() {
        accountService.transfer();
    }
}
