package org.anonymous.service;

import org.anonymous.beans.Account;

import java.util.List;

/**
 * @author child
 * 2019/4/13 17:02
 */
public interface AccountService {
    default void save() {
    }

    default void save(Account account) {
    }

    default void delete(int id) {
    }

    default void update(Account account) {
    }

    default List<Account> find() {
        return null;
    }

    default void transfer() {
    }

}
