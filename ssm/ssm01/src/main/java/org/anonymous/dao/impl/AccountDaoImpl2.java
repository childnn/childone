package org.anonymous.dao.impl;

import org.anonymous.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author child
 * 2019/4/14 12:09
 */
//@Component(value = "accountDao")  //等价于: <bean id="accountDao" class="org.anonymous.daoimpl.AccountDaoImpl">
@Repository("accountDao1") //解析 注解，则创建 对象，放入 ioc 容器
public class AccountDaoImpl2 implements AccountDao {
    @Autowired //自动去 Spring 容器中，找符合类型的对象，自动注入，替代 set 方法
    private final JdbcTemplate jdbcTemplate;

    public AccountDaoImpl2(JdbcTemplate jdbcTemplate) { //没有空参构造也可以?.......
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save() {
        String sql = "INSERT INTO account VALUES (NULL, 'jack', 3333)";
        System.out.println("org.anonymous.daoimpl.AccountDaoImpl: notice");
        jdbcTemplate.update(sql);
    }
}
