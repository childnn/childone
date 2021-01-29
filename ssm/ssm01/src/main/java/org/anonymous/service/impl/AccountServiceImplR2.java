package org.anonymous.service.impl;


import org.anonymous.dao.AccountDao;
import org.anonymous.service.AccountService;

/**
 * @author child
 * 2019/4/19 9:55
 * 声明式事务: 全 xml 方式
 */
public class AccountServiceImplR2 implements AccountService {

    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void save() {
        accountDao.save();
    }

    @Override //转账
    public void transfer() {
        accountDao.outMoney();
        //        int i = 1 / 0;
        accountDao.inMoney();
    }
}
