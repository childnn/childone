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
 * 2019/4/17 16:07
 * 硬编码: 改变源码使用事务: org.anonymous.service.serviceimpl.AccountServiceImpl
 * 不使用 aop
 */
@RunWith(SpringJUnit4ClassRunner.class) //指定 配置文件的 加载类(固定写法)
@ContextConfiguration(locations = "classpath:bean.xml") //指定配置文件 locations/配置类 classes
public class DemoTx {

    @Autowired //di: 不使用 配置的方式- 没必要把 测试类 交给 ioc 管理
    @Qualifier("accountServiceImpl") //接口有多个实现类 时,使用 qualifier
    private AccountService accountService; //或者不写 @Qualifier(), 将变量名==id

    @Test // 硬编码事务
    public void test1() {
        accountService.transfer();
    }

}
