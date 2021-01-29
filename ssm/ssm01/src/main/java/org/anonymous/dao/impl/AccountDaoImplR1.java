package org.anonymous.dao.impl;

import org.anonymous.dao.AccountDao;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * @author child
 * 2019/4/19 9:53
 * 继承 父类的 jdbcTemplate: 不能用注解了
 */
public class AccountDaoImplR1 extends JdbcDaoSupport implements AccountDao {

    @Override
    public void save() {
        String sql = "";
        getJdbcTemplate().update(sql);
    }

    @Override //加钱
    public void inMoney() {
        String sql = "update account set money = money - ? where id = ?";

        getJdbcTemplate().update(sql, 1, 5);
    }

    @Override //减钱
    public void outMoney() {
        String sql = "update account set money = money + ? where id = ?";
        getJdbcTemplate().update(sql, 1, 7);
    }
}
