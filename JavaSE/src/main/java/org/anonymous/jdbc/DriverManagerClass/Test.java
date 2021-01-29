package org.anonymous.jdbc.DriverManagerClass;

/**
 * 2019年3月9日10:03:33
 * static void registerDriver(Driver driver) 向 DriverManager 注册给定驱动程序。
 * 实际应用不会使用该静态方法,该方法加载了驱动两次: 方法加载一次, 参数 new Driver() 加载一次
 * <p>
 * com.mysql.jdbc.Driver 驱动类
 * 源码: 静态代码块,随着类的加载而加载
 * //
 * // Register ourselves with the DriverManager
 * //
 * static {
 * try {
 * java.sql.DriverManager.registerDriver(new Driver());
 * } catch (SQLException E) {
 * throw new RuntimeException("Can't register driver!");
 * }
 * }
 * static Connection getConnection(String url, String user, String password) 试图建立到给定数据库 URL 的连接。
 * url: 指定连接的路径
 * 语法: jdbc:mysql://ip(域名):端口号/数据库名characterEncoding=utf8;
 * eg: jdbc:mysql://localhost:3306/数据库名
 * 细节: 如果连接的是本机mysql服务器,并且 mysql 服务器默认端口是 3306, 则 URL 可以简写为: jdbc:mysql:///数据库名
 */
public class Test {
    public static void main(String... args) {
        System.out.println(2.0 - 1.1); //0.8999999999999999
        //\u00A0       System.out.println("\uffff");
        System.out.println("\u2122"); //™
        System.out.println("\u03C0"); //π
        System.out.println("\u005B"); //[
        System.out.println("\u005D"); //]
    }
}
