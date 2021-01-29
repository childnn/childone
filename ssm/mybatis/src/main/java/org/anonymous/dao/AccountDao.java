package org.anonymous.dao;

import org.anonymous.domain.Account;

import java.util.List;

/**
 * @author child
 * 2019/4/12 13:31
 */
public interface AccountDao {
    //左外: 全查
    List<Account> findList();

    //查询账户
    List<Account> findAccount();

    //根据用户的 id 查询账户
    List<Account> findAccountByUId(int id);
}
