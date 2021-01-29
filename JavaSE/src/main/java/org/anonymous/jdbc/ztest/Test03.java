package org.anonymous.jdbc.ztest;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

/**
 * 2019年3月10日09:17:28
 * PreparedStatement 接口
 * 用户登录,工具类,
 */
public class Test03 {
    private static PreparedStatement preparedStatement;
    private static Connection connection;
    private static ResultSet resultSet;

    public static void main(String[] args) {
        System.out.println("请输入用户名:");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        System.out.println("请输入密码:");
        String password = sc.nextLine();
        login(name, password);
    }

    private static void login(String name, String password) {
        try {
            //通过工具类,获取驱动,连接
            connection = Jutil.getConnection();
            //获取执行对象: PreparedStatement
            //在获取对象时,定义 sql 语句: 与 Statement 区别
            preparedStatement = connection.prepareStatement("select * from users where uname = ? and upassword = ?");
            //给 占位符 赋值
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Jutil.close(connection, preparedStatement, resultSet);
        }
    }
}

class Jutil { //工具类: 创建连接,销毁连接
    private static final Properties pro = new Properties();

    //静态代码块: 在类加载时,加载配置文件.获取数据库 驱动
    static {
        try {
            //加载配置文件
            InputStream is = Jutil.class.getResourceAsStream("users.properties");
            pro.load(is);
            //获取 集合的 value : 驱动
            Class.forName(pro.getProperty("driver"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接对象 : 通过集合 的 key 获取 value: url user password
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(pro.getProperty("url"), pro.getProperty("user"), pro.getProperty("password"));
    }

    //关闭资源: 增删改
    public static void close(Connection connection, PreparedStatement preparedStatement) {
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

    //关闭资源: 查
    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
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
        if (null != resultSet) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}