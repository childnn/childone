package org.anonymous.util;

import org.anonymous.domain.User;

/**
 * @author child
 * 2019/4/12 22:16
 * sql 工具类: 动态sql
 */
public class SqlUtils {
    public String selectSql(User user) {
        String sql = "select * from user where true";
        if (user.getUsername() != null && !"".equals(user.getUsername())) {
            sql += " and username = #{username}";
        }
        System.out.println(sql);
        return sql;
    }
}
