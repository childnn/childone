package org.anonymous.dao;

import org.anonymous.beans.Account;

import java.util.List;

/**
 * @author child
 * 2019/4/17 21:23
 */
public interface AccountDaoR {
    //曾
    void insert(Account account);

    //删
    void deleteById(int id);

    //改
    void update(Account account);

    //全查
    List<Account> retrieve();

    //根据 id 查
    Account findById(int id);

    //聚合查
    String count();
}
