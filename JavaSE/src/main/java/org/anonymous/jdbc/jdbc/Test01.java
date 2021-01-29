package org.anonymous.jdbc.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 2019年3月9日10:58:40
 * 使用jdbc 增删改 表中数据: DML,data manipulation language (数据操作语言)
 * insert/delete/update
 */
public class Test01 {
    public static void main(String[] args) {
        Statement statement = null;
        Connection connection = null;
        try {
            //注册驱动
            Class.forName("class.mysql.jdbc.Driver");
            //定义 sql
            String sql = "inset into 表明(字段列表) values(值列表)";
            //获取 Connection 对象
            connection = DriverManager.getConnection("jdbc:mysql:///数据库名", "root", "root");
            //获取执行 sql 的对象 Statement
            statement = connection.createStatement();
            //执行 sql 语句
            int count = statement.executeUpdate(sql);
            //处理结果
            System.out.println(count);
            if (count > 0) {
                System.out.println("添加成功");
            } else {
                System.out.println("添加失败");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally { // 为了避免释放资源失败,将释放资源的代码放在 finally 中
            //非空校验
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
