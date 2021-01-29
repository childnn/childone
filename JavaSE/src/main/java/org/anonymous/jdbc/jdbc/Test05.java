package org.anonymous.jdbc.jdbc;

import org.anonymous.jdbc.beanUtils.JDBCUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 2019年3月9日13:54:16
 * 把查询结果封装为类
 * 封装 emp 表的javabean
 * beanUtils 工具类
 */
public class Test05 {
    public static void main(String[] args) {
        //        List<Emp> list = findAll();
        //        list.forEach(System.out::println);
        List<Emp> list = find();
        list.forEach(System.out::println);
    }

    //    @Test //junit
    public static List<Emp> find() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Emp> list = new ArrayList<>();
        try {
            connection = JDBCUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from emp;");
            while (resultSet.next()) {
                list.add(new Emp(resultSet.getInt("id"), resultSet.getString("ename"),
                        resultSet.getInt("job_id"), resultSet.getInt("mgr"), resultSet.getDate("joindate"),
                        resultSet.getDouble("salary"), resultSet.getDouble("bonus"),
                        resultSet.getInt("dept_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JDBCUtils.close(resultSet, statement, connection);
        return list;
    }

    //查询表中所有对象(一条记录就是一个对象)
    private static List<Emp> findAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Emp> list = new ArrayList<>();
        //注册驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "root");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from emp;");
            while (resultSet.next()) {
                list.add(new Emp(resultSet.getInt("id"), resultSet.getString("ename"),
                        resultSet.getInt("job_id"), resultSet.getInt("mgr"), resultSet.getDate("joindate"),
                        resultSet.getDouble("salary"), resultSet.getDouble("bonus"),
                        resultSet.getInt("dept_id")));
            }
        } catch (ClassNotFoundException | SQLException e) {
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
            if (null != resultSet) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}

class Emp {
    private final int id;
    private final String ename;
    private final int job_id;
    private final int mgr;
    private final Date joindate;
    private final double salary;
    private final double bonus;
    private final int dept_id;

    public Emp(int id, String ename, int job_id, int mgr, Date joindate, double salary, double bonus, int dept_id) {
        this.id = id;
        this.ename = ename;
        this.job_id = job_id;
        this.mgr = mgr;
        this.joindate = joindate;
        this.salary = salary;
        this.bonus = bonus;
        this.dept_id = dept_id;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", ename='" + ename + '\'' +
                ", job_id=" + job_id +
                ", mgr=" + mgr +
                ", joindate=" + joindate +
                ", salary=" + salary +
                ", bonus=" + bonus +
                ", dept_id=" + dept_id +
                '}';
    }
}