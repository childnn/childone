package org.anonymous.dao.impl;

import org.anonymous.beans.Account;
import org.anonymous.dao.AccountDaoR;
import org.anonymous.util.MyRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author child
 * 2019/4/17 21:26
 */
public class AccountDaoImplR extends JdbcDaoSupport implements AccountDaoR {
  /*  private JdbcTemplate jdbcTemplate;
    //di
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }*/

    /**
     * 第二种方式 引入 jdbcTemplate: 继承 JdbcDaoSupport, 其中有 private JdbcTemplate jdbcTemplate;
     * 而 JdbcTemplate 已经 在 ioc 中了
     * 使用 父类 getJdbcTemplate()
     * 只能用于 xml, 不能用与注解
     */

    @Override
    public void insert(Account account) {
        String sql = "insert into account values(?, ?, ?)";
        //        jdbcTemplate.update(sql, account.getId(), account.getName(), account.getMoney());
        getJdbcTemplate().update(sql, account.getId(), account.getName(), account.getMoney());
    }

    @Override
    public void deleteById(int id) {
        String sql = "delete from account where id = ?";
        getJdbcTemplate().update(sql, id);
    }

    @Override
    public void update(Account account) {
        String sql = "update account set name = ? where id = ?";
        getJdbcTemplate().update(sql, account.getName(), account.getId());
    }

    @Override
    public List<Account> retrieve() {
        String sql = "select * from account";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(Account.class));
    }

    @Override //使用 自定义的 封装对象 工具类
    public Account findById(int id) {
        //使用别名： jdbcTemplate 本身不支持别名，只能自定义工具类
        String sql = "select id aid, name aname, money amoney from account where id = ?";

        return getJdbcTemplate().queryForObject(sql, new MyRowMapper(), id);
    }

    @Override
    public String count() {
        //        return jdbcTemplate.queryForObject("select count(*) from account", int.class);
        return getJdbcTemplate().queryForObject("select count(*) from account", String.class);
    }

}
