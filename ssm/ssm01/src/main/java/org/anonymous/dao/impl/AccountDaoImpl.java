package org.anonymous.dao.impl;

import org.anonymous.dao.AccountDao;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author child
 * 2019/4/14 9:51
 */
public class AccountDaoImpl implements AccountDao {
    //把 jdbcTemplate 作为属性: 依赖注入
    private JdbcTemplate jdbcTemplate/* = new JdbcTemplate(datasource)*/;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void delete(int id) {
        String sql = "delete from account where id = ?";
        jdbcTemplate.update(sql, id);
        //        System.out.println(jdbcTemplate);
    }
}
