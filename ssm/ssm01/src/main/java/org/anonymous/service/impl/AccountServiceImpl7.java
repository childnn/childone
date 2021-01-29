package org.anonymous.service.impl;

import org.anonymous.beans.Account;
import org.anonymous.dao.AccountDao;
import org.anonymous.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author child
 * 2019/4/14 16:41
 */
@Service("accountService7")
public class AccountServiceImpl7 implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public void save(Account account) {
        accountDao.save(account);
    }

    @Override
    public void delete(int id) {
        accountDao.delete(id);
    }

    @Override
    public void update(Account account) {
        accountDao.update(account);
    }

    @Override
    public List<Account> find() {
        return accountDao.find();
    }
}
