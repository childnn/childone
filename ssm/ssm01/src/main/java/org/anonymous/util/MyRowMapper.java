package org.anonymous.util;

import org.anonymous.beans.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author child
 * 2019/4/17 22:27
 * 自定义对象封装工具类： jdbcTemplate 自带的对象封装工具类（BeanPropertyRowMapper）
 * 要求： javabean 的 属性与 数据库中 表的字段名必须完全一致，才能封装成功
 * 也就是说，不支持别名映射关系
 * 因此，如果有别名关系，只能自定义 封装数据为对象的工具类
 */
public class MyRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {
        Account account = new Account();
        //封装
        account.setId(resultSet.getInt("aid")); //通过别名 获取 结果集 中的数据
        account.setName(resultSet.getString("aname"));
        account.setMoney(resultSet.getInt("amoney"));

        return account;
    }
}
