package org.anonymous.dao.impl;

import org.anonymous.dao.AccountDao;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author child
 * 2019/4/17 15:25
 */
public class AccountDaoImpl4 implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    @Override //减钱
    public void outMoney() {
        String sql = "update account set money = money - ? where name = ?";
        jdbcTemplate.update(sql, 1, "jack");
    }

    @Override //加钱
    public void inMoney() {
        String sql = "update account set money = money + ? where name = ?";
        jdbcTemplate.update(sql, 1, "rose");
    }

    //di
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
