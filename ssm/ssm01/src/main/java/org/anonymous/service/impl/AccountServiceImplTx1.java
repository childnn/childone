package org.anonymous.service.impl;

import org.anonymous.dao.AccountDao;
import org.anonymous.service.AccountService;

/**
 * @author child
 * 2019/4/17 18:21
 * 切面类+通知: 管理事务-- 不改变源代码
 */
public class AccountServiceImplTx1 implements AccountService {
    private AccountDao accountDao;

    @Override
    public void transfer() {
        accountDao.outMoney();
        int i = 1 / 0;
        accountDao.inMoney();
    }

    //di
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
