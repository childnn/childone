package org.anonymous.test.spring;

import org.anonymous.beans.Account;
import org.anonymous.dao.AccountDaoR;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author child
 * 2019/4/17 21:34
 * 连接池: druid, c3p0, spring, mybatis
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean-r1.xml")
public class SpringJunit {
    @Autowired
    @Qualifier("accountDaoImplR")
    private AccountDaoR accountDao;

    @Test //删
    public void test() {
        accountDao.deleteById(4);
    }

    @Test //曾
    public void test1() {
        accountDao.insert(new Account(3, "black", 100));
    }

    @Test //改
    public void test2() {
        accountDao.update(new Account(2, "white", 100));
    }

    @Test //查所有
    public void test3() {
        List<Account> list = accountDao.retrieve();
        list.forEach(System.out::println);
    }

    @Test //根据 id 查: 有别名, 使用 自定义的 BeanRowMapper implements RowMapper<T>
    public void test4() {
        Account account = accountDao.findById(1);
        System.out.println(account);
    }

    @Test //计数: String 也可以
    public void test5() {
        String count = accountDao.count();
        System.out.println(count);
    }
}
