package org.anonymous.jdbc.ztest;

import org.anonymous.jdbc.beanUtils.JDBCUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

/**
 * 2019年3月9日15:59:04
 * 用户登录
 */
public class Test {
    private static final Properties pro = new Properties();
    private static Connection connection;
    private static Statement statement; //使用 Statement 接口的子类对象,创造 sql 注入问题
    private static ResultSet resultSet;

    public static void main(String[] args) throws IOException {
        //        register();
        login();
    }

    //用户注册: 存入配置文件
    private static void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("欢迎注册!召唤师");
        System.out.println("用户名:");
        String uname = scanner.nextLine();
        try {
            pro.load(new FileReader(Test.class.getClassLoader().getResource("users.properties").getPath()));
            if (pro.containsKey(uname)) {
                System.out.println("用户名已存在!请重新输入");
            } else {
                System.out.println("请输入密码:");
                String upassword = scanner.nextLine();
                System.out.println("请确认密码:");
                String s = scanner.nextLine();
                if (!Objects.equals(s, upassword)) {
                    System.out.println("两次密码不匹配!");
                    return;
                }
                try {
                    pro.setProperty(uname, upassword);
                    //            System.out.println(path); //测试
                    pro.store(new FileWriter(Test.class.getClassLoader().getResource("users.properties").getPath()), "users data");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //把集合中的数据存入数据库的表中
        try {
            //获取mysql驱动,获取连接对象
            connection = JDBCUtils.getConnection();
            statement = connection.createStatement();
            Set<Map.Entry<Object, Object>> entries = pro.entrySet();
            for (Map.Entry<Object, Object> entry : entries) {
                Object key = entry.getKey();
                if (!Objects.equals(key, "driver") && !Objects.equals(key, "url") && !Objects.equals(key, "user") && !Objects.equals(key, "password")) {
                    Object value = entry.getValue();
                    statement.execute("insert into users(uname, upassword) values('" + key + "','" + value + "')");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally { //释放资源
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

    //用户登录
    private static void login() {
        System.out.println("请输入用户名:");
        Scanner sc = new Scanner(System.in);
        String uname = sc.nextLine();
        System.out.println("请输入密码:");
        String upassword = sc.nextLine();
        //非空校验
        if (null == uname || null == upassword) {
            System.out.println("用户名或密码错误!");
        } else {
            try {
                connection = JDBCUtils.getConnection();
                statement = connection.createStatement();
                String sql = "select * from users where uname = '" + uname + "' and upassword = '" + upassword + "'";
                //java 中的字符串,必须加上单引号,或者双引号才能试用 数据库
                resultSet = statement.executeQuery(sql);
                System.out.println(sql);
                //sql 注入问题: 当密码输入 (a' or 'a' = 'a) 时, or 变成了 sql 语句的一部分,改变了实际的sql语法
                //此时需要使用 PreparedStatement 见 Test03
                //sql 查询语句 的条件查询的结果: 要么没有,要么只有一行(用户名和密码同时相同, 结果唯一)
                if (resultSet.next()) { //关键: next() 结果为true,即表明数据库已经找到 用户名-密码完全匹配的用户
                    System.out.println("登录成功!");
                } else {
                    System.out.println("用户名或密码错误!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally { //释放资源
                JDBCUtils.close(resultSet, statement, connection);
            }
        }
    }
}
