package org.anonymous.jdbc.ztest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 2019年3月9日21:07:24
 * PreparedStatement 接口
 * 1.jdbc 与数据库中表的数据的crud
 * *      Statement//PreparedStatement
 * *  2.JDBCUtil 与 配置文件
 * *  3.登录案例--> sql 注入的问题
 * *  4.事务
 */
public class Test02 {
    private static PreparedStatement preparedStatement;
    private static Connection connection;
    private static ResultSet resultSet;

    public static void main(String[] args) {
        insert();
    }

    //利用 PreparedStatement 向表中添加数据
    private static void insert() {
        try {
            //获取mysql驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取数据库连接对象
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
            //获取 sql 执行对象
            preparedStatement = connection.prepareStatement("insert into students(id, name, class) values(6, '李奎奎', '5班');");
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != connection) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
