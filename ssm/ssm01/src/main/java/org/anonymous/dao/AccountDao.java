package org.anonymous.dao;

import org.anonymous.beans.Account;

import java.util.List;

/**
 * @author child
 * 2019/4/14 9:50
 */
public interface AccountDao {
    default void delete(int id) {
    }

    default void save() {
    }

    default void save(Account account) {
    }

    default void update(Account account) {
    }

    default List<Account> find() {
        return null;
    }

    //减钱
    default void outMoney() {
    }

    //加钱
    default void inMoney() {
    }
}
