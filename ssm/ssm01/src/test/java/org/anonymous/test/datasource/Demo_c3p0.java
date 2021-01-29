package org.anonymous.test.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author child
 * 2019/4/14 10:02
 * c3p0 回顾: 硬编码
 */
public class Demo_c3p0 {
    public static void main(String[] args) throws PropertyVetoException, SQLException {
        //导入 c3p0 坐标 -- 也可以设置 c3p0-config.xml, c3p0 自动加载该配置文件,driver,user,password,并注册驱动
        //创建 c3p0 连接池
        //设置连接信息
        //从连接池获取链接

        //空参构造
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        //set 属性: di(依赖注入)
        comboPooledDataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/springdb");
        comboPooledDataSource.setUser("root");
        comboPooledDataSource.setPassword("root");
        Connection connection = comboPooledDataSource.getConnection();
        System.out.println(connection);
    }
}
