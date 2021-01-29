package org.anonymous.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;

/**
 * @author child
 * 2019/4/24 11:32
 * 希望解决 继承 JdbcDaoSupport 之后不能用注解的问题
 * JdbcDaoSupport 是 抽象类, 想注册到 ioc,只能使用其子类
 */
@Deprecated
public class JdbcSupport extends JdbcDaoSupport {

    private DataSource dataSource;

    public JdbcTemplate getTemplate() {

        return getJdbcTemplate();
    }
}
