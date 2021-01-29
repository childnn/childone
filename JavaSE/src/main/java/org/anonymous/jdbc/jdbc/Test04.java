package org.anonymous.jdbc.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 2019年3月9日12:09:07
 * 增删改查表中数据
 * inset into 表明(字段列表) values(值列表)
 * delete from 表明 where 条件
 * update 表明 set 字段=值.. where 条件
 */
public class Test04 {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "root");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from emp");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + "--" + resultSet.getString("ename") +
                        "--" + resultSet.getInt("job_id") + "--" + resultSet.getInt("mgr") +
                        "--" + resultSet.getDate("joindate") + "--" + resultSet.getBigDecimal("salary")
                        + "--" + resultSet.getBigDecimal("bonus") + "--" + resultSet.getInt("dept_id"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (null != connection) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
