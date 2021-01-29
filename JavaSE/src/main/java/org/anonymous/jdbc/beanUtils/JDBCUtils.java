package org.anonymous.jdbc.beanUtils;

import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 2019年3月9日15:04:37
 */
public class JDBCUtils {
    private static final Properties pro;

    static { //读取配置文件 jdbc.properties //静态代码块在类加载时加载
        pro = new Properties();
        try {
            //动态获取 src 路径下的文件的方式: 类加载器
            ClassLoader classLoader = JDBCUtils.class.getClassLoader();
            URL resource = classLoader.getResource("users.properties"); //URL 对象,统一资源定位符
            //            System.out.println(resource);
            String path = resource.getPath(); //获取的实际上就是配置文件的位置
            //            System.out.println(path);
            pro.load(new FileReader(path));
            Class.forName(pro.getProperty("driver")); //只需要加载驱动一次
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(pro.getProperty("url"), pro.getProperty("user"), pro.getProperty("password"));
    }

    public static void close(Statement statement, Connection connection) { //增删改
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

    //只有查询数据库中的数据时,才需要 ResultSet 接口的实现类对象,因此单独定义 query 的关闭资源方法
    public static void close(ResultSet resultSet, Statement statement, Connection connection) { //查
        if (null != resultSet) {
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
