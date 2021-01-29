package org.anonymous.jdbc.ztest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 2019年3月9日18:23:31
 * 1.jdbc 与数据库中表的数据的crud
 * Statement//PreparedStatement
 * 2.JDBCUtil 与 配置文件
 * 3.登录案例--> sql 注入的问题
 * 4.事务
 */
public class Test01 {
    private static Connection connection; //静态方法只能访问属性
    private static Statement statement;
    private static ResultSet resultSet;

    public static void main(String[] args) {
        //        crate(); //创建表
        //        drop(); //删除表
        //        alter(); //改变表:rename to,modify,change
        //        insert(); //增加表数据:记录
        //        update(); //更新
        //        query(); //查询
    }

    private static void query() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from students");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + "--" + resultSet.getString("name") + "--" + resultSet.getString("class"));
            }
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
            if (null != statement) {
                try {
                    statement.close();
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

    //增加表中的数据
    private static void insert() {
        try {
            //获取mysql的java驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取数据库连接对象
            connection = DriverManager.getConnection("jdbc:mysql:///test", "root", "root");
            //获取sql执行对象
            statement = connection.createStatement();
            //执行
            statement.executeUpdate("insert into students(id, name, class) values(3, '张三', '2班'), (4, '李四', '3班')"); //返回受影响的行数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //更新表中数据
    private static void update() {
        try {
            //获取驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取数据库连接对象
            connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "root");
            //获取sql语句执行对象
            statement = connection.createStatement();
            statement.executeUpdate("update students set name = '小娟' where id = 1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

    //改: 字段
    private static void alter() { //add,modify,rename to,change
        try {
            //创建驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "root");
            //获取sql语句执行对象: Statement
            statement = connection.createStatement();
            //            boolean execute = statement.execute("alter table student add class varchar(100)");
            //            statement.execute("alter table student rename to students");
            //            System.out.println(execute);
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
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //删除表
    private static void drop() {
        try {
            //获取驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接: Connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "root");
            //获取执行sql语句对象: Statement
            statement = connection.createStatement();
            boolean execute = statement.execute("drop table student");
            System.out.println(execute);
        } catch (Exception e) {
            e.printStackTrace();
        } finally { //关闭资源
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

    //创建表 : create table 表明(字段/数据类型 列表)
    private static void crate() {
        try {
            //获取 数据库驱动: 由相应的数据库厂商提供的一套操作数据库的java jar包 //一套.class 可执行文件
            Class.forName("com.mysql.jdbc.Driver");
            //获取 数据库连接对象: Connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "root");
            //获取执行对象: Statement
            statement = connection.createStatement();
            //如果第一个结果为 ResultSet 对象，则返回 true；如果其为更新计数或者不存在任何结果，则返回 false
            boolean execute = statement.execute("create table student(id int primary key, name varchar(100));");
            System.out.println(execute);
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
