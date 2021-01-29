package org.anonymous.test.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.SQLException;

/**
 * @author child
 * 2019/4/14 10:29
 */
public class Demo_druid {
    public static void main(String[] args) throws SQLException {
        //到 druid 坐标
        //创建 druid 连接池
        //设置连接信息
        //从连接池获取连接

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql:///springdb");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        DruidPooledConnection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
