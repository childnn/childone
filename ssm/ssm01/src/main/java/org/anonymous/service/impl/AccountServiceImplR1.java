package org.anonymous.service.impl;

import org.anonymous.dao.AccountDao;
import org.anonymous.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author child
 * 2019/4/19 9:55
 * 声明式事务: 半 xml 方式
 */
@Service("accountServiceImplR1") //ioc
//1. 在 xml 中 开启 事务注解支持
//2. 使用注解 配置 事务特性: 可以使用在方法上,也可以使用在 类上, 如果都用了, 就近原则(以方法上的为准)
//@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = -1)
public class AccountServiceImplR1 implements AccountService {
    //    InitializingBean // @PostConstruct //init-method

    @Autowired
    @Qualifier("accountDaoImpl1")
    private AccountDao accountDao;

    @Override
    public void save() {
        accountDao.save();
    }

    @Override //转账
    @Transactional(transactionManager = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED)
    //配置方法上: 指定一个方法
    public void transfer() {
        accountDao.outMoney();
        //        int i = 1 / 0;
        accountDao.inMoney();
    }
}
