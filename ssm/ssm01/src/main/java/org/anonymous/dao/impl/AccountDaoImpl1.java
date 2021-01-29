package org.anonymous.dao.impl;

import org.anonymous.dao.AccountDao;
import org.springframework.stereotype.Repository;

/**
 * @author child
 * 2019/4/14 14:09
 */
@Repository("accountDao0")
public class AccountDaoImpl1 implements AccountDao {
    @Override
    public void save() {
        System.out.println("00000000000");
    }
}
