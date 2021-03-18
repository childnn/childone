package org.anonymous.classloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/2/2 13:44
 * 实际上 系统类加载器 {@link sun.misc.Launcher.AppClassLoader} 扩展类加载器 {@link sun.misc.Launcher.ExtClassLoader}
 * 均继承自 {@link java.net.URLClassLoader}
 * @see java.net.URLClassLoader#URLClassLoader(java.net.URL[]) 使用默认的父类加载器创建一个 ClassLoader 对象, 该对象从
 *      指定的系列路径来查询并加载类.
 * @see java.net.URLClassLoader#URLClassLoader(java.net.URL[], ClassLoader) 使用指定的父类加载器创建一个 ClassLoader
 *      对象, 其他功能与前一个构造器相同.
 * 一旦得到了 URLClassLoader 对象之后, 就可以调用该对象的 {@link java.net.URLClassLoader#loadClass(String)} 方法来加载指定类.
 * ----
 * MySQL 参数设置参见: {@link com.mysql.cj.conf.PropertyKey}
 */
public class URLClassLoaderTest {

    private static Connection conn;

    // mysql-driver 的路径, 有需要可以修改.
    // 本地文件使用 file:/// 协议. -- 协议后的 /// 可以省略.
    static final String MYSQL_JAR_LOCATION = "file:D:\\develop\\maven\\repository\\mysql\\mysql-connector-java\\8.0.22/mysql-connector-java-8.0.22.jar";

    static Connection getConn(String url, String username, String password) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        if (conn == null) {
            URL[] urls = {new URL(MYSQL_JAR_LOCATION)};
            // 以默认的 ClassLoader 作为父 ClassLoader, 创建 URLClassLoader
            final URLClassLoader myClassLoader = new URLClassLoader(urls);
            // 加载 MySQL 的 JDBC 驱动, 并创建默认实例
            final Class<?> driverClass = myClassLoader.loadClass("com.mysql.cj.jdbc.Driver");

            // Driver 实际不在当前 Classpath 下, 不可这么写.
            // Class.forName(driverClass.getName()); // java.lang.ClassNotFoundException: com.mysql.cj.jdbc.Driver

            final Driver driver = (Driver) driverClass.newInstance();

            // 这两个参数的 key 是固定的, mysql 配置要求, 不可改变
            // MySQL 参数设置参见: {@link com.mysql.cj.conf.PropertyKey}
            conn = driver.connect(url, new Properties() {{
                put("user", username);
                put("password", password);
            }});

           // conn = DriverManager.getConnection(url, username, password);
        }
        return conn;
    }

    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException, InstantiationException, SQLException, IllegalAccessException {
        System.out.println(getConn("jdbc:mysql://localhost:3306/test", "root", "root"));
    }

}
