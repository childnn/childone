package org.anonymous.service.impl;

import org.anonymous.dao.AccountDao;
import org.anonymous.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author child
 * 2019/4/14 14:31
 */
@Service("accountService1") //<bean id="accountService1" class=...>
@Scope("prototype") //开启多例
public class AccountServiceImpl4 implements AccountService {

    @Autowired
    @Qualifier("accountDao0") //多个实现类对象注入冲突的解决方式二: 指定 id
    private AccountDao accountDao;

    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    @Override
    public void save() {
        //        accountDao.save(); //org.anonymous.daoimpl.AccountDaoImpl0
        //        System.out.println(driver);
        //        System.out.println(url);
        //        System.out.println(username);
        System.out.println(password);
    }

    @PostConstruct // init-method  -- 这是 jdk 自带注解: javax.annotation - 需要导入坐标
    public void aaa() {
        System.out.println("init-method");
    }

    @PreDestroy // destroy-method
    public void bbb() {
        System.out.println("destroy-method");
    }

}
