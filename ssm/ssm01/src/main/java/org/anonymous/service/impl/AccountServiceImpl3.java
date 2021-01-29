package org.anonymous.service.impl;

import org.anonymous.dao.AccountDao;
import org.anonymous.service.AccountService;

/**
 * @author child
 * 2019/4/14 9:50
 */
public class AccountServiceImpl3 implements AccountService {
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void delete(int id) {
        //调用 dao
        accountDao.delete(id);
    }
}
