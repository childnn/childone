package org.anonymous.jdbc.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * query 表中数据
 */
public class Test03 {
    public static void main(String[] args) {
        Statement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("");
            String sql = "";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) { //让游标移动到下一行//游标起始位置在 第一条记录之前//如果不存在下一行,返回false
                int id = resultSet.getInt(1); //获取第一个字段的记录数据
                String field = resultSet.getString("字段名"); //获取指定字段名的记录数据
                System.out.println("记录信息");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally { //释放资源
            if (resultSet != null) {
                try {
                    resultSet.close();
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
            if (null != connection) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
