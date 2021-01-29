package org.anonymous.dao.impl;

import org.anonymous.beans.Account;
import org.anonymous.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author child
 * 2019/4/14 16:40
 */
@Repository("accountDao3")
public class AccountDaoImpl3 implements AccountDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Account account) {
        String sql = "INSERT INTO account VALUES(NULL, ?, ?)";
        jdbcTemplate.update(sql, account.getName(), account.getMoney());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM account WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void update(Account account) {
        String sql = "UPDATE account SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, account.getName(), account.getId());
    }

    @Override
    public List<Account> find() {
        String sql = "SELECT * FROM account";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Account.class));

    }
}
