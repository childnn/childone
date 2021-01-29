package org.anonymous.jdbc.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * 2019年3月9日10:45:29
 * 重点中的重点!!!
 * jdbc: java database connectivity java数据库连接,用java语言操作数据库 //数据库使用 sql 语言,java 使用 java 语言
 * sun公司 定义了操作所有 关系型数据库 的规则: 一套java接口(java.sql 包),由不同的数据库供应者自行完成 接口的实现类(数据库的驱动jar包).--数据库驱动
 * 常用关系型数据库: mysql,oracle,DB2
 * 导入需要使用的数据库厂商提供的驱动(实现类jar包),通过 多态 操作数据库: 使用的是 java 接口,但实际起作用的是 对应数据库的 驱动
 * 步骤:
 * 1.导入驱动
 * 2.注册驱动
 * 3.获取数据库连接对象
 * 4.定义sql
 * 5.获取执行sql对象 Statement
 * 6.执行sql
 * 7.处理结果
 * 8.释放资源
 * <p>
 * 一般不会使用java代码 建库建表
 * 只会使用java代码操作表中的数据: 以查询为主//Statement类的 ResultSet executeQuery(String sql) 方法
 * DriverManager 类: 注册驱动.管理一组 JDBC 驱动程序的基本服务。
 * static void registerDriver(Driver driver) 向 DriverManager 注册给定驱动程序。
 * 一般直接使用反射 : Class.forName(String driver) 加载
 * Connection 接口: 与特定数据库的连接（会话）。在连接上下文中执行 SQL 语句并返回结果。
 * Statement 接口: 用于执行 静态 SQL 语句并返回它所生成结果的对象。
 * boolean execute(String sql) 执行给定的 SQL 语句，该语句可能返回多个结果。
 * int executeUpdate(String sql) 执行给定 SQL 语句，该语句可能为(DML 增删改 表中的数据) INSERT、UPDATE 或 DELETE 语句，或者不返回任何内容的 SQL 语句（如 SQL DDL 语句）。
 * 执行 DML 操作表中的数据 返回受影响的表中 记录行数 //执行 DDL 操作表 返回 0
 * ResultSet executeQuery(String sql) 执行给定的 SQL 语句，该语句返回单个 ResultSet 对象。 //DQL 语句 //数据查询语句,重点!
 * 返回结果集 对象
 * ResultSet: 结果集
 * PreparedStatement 接口: Statement 接口的子接口. 表示预编译(动态)的 SQL 语句的对象。
 * //预编译: Connection 接口的 PreparedStatement prepareStatement(String sql): 在生成 sql 执行对象PreparedStatement 时, sql 格式已经被传入的参数 固定好了
 * SQL 注入问题: 在使用 Statement 接口操作 sql 语句时, 有一些 sql 的特殊关键字与字符串的拼接,会造成安全性问题
 * eg: 用户名随便输入//密码输入: a' or 'a' = 'a; //关键字 or 右边恒成立, 把输入的参数作为了sql语句 语法的一部分
 * 解决SQL注入问题: 使用 PreparedStatement 接口 (实现类对象)
 * 预编译的SQL: 参数使用英文问号?作为占位符
 * 步骤:
 * 1. 导入驱动包: mysql-connector-java-5.1.37-bin.jar
 * 2. 注册驱动
 * 3. 获取数据库连接对象: Connection
 * 4. 定义sql 语句 : 使用?作为占位符
 * select * from user where username = ? and password = ?
 * 5. 获取sql语句执行对象: 通过 Connection 接口的方法:
 * PreparedStatement prepareStatement(String sql) 创建一个 PreparedStatement 对象来将参数化的 SQL 语句发送到数据库。
 * 定义 ?  的值
 * PreparedStatement 接口的方法: set字段数据类型(第几个?, 实参值)
 * 如 setString(int parameterIndex, String x)
 * 6. 执行sql,返回结果
 * ResultSet executeQuery() 在此 PreparedStatement 对象中执行 SQL 查询，并返回该查询生成的 ResultSet 对象。
 * 7. 释放资源
 * <p>
 * JDBC与事务:
 * Connection 接口的方法:
 * void setAutoCommit​(boolean autoCommit) 将此连接的自动提交模式设置为给定状态。
 * boolean getAutoCommit() 获取此 Connection 对象的当前自动提交模式。
 * void commit() 使所有上一次提交/回滚后进行的更改成为持久更改，并释放此 Connection 对象当前持有的所有数据库锁。
 * void rollback() 取消在当前事务中进行的所有更改，并释放此 Connection 对象当前持有的所有数据库锁。
 * 把 rollback() 放在 catch 代码块中: 发生异常,则 rollback()
 */
public class Test {
    public static void main(String[] args) throws Exception {
        //加载驱动 : mysql5之后的驱动jar包可以省略注册驱动的步骤
        Class.forName("com.mysql.jdbc.Driver"); //自动注册驱动
        //获取数据库连接:Connection 对象
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db3", "root", "root");
        //创建sql语句
        String sql = "update 表 set 字段 = 值 where 条件 "; //指定的 sql 语句字符串形式
        //通过Connection 对象获取可执行的 sql 语句对象
        Statement statement = connection.createStatement();
        //通过 Statement 对象 可以操作 sql 语句 (增删改)
        int count = statement.executeUpdate(sql); //返回受影响的行数
        //释放资源
        statement.close();
        connection.close();
    }
}
