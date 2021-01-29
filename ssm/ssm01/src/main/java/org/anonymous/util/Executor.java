package org.anonymous.util;

import org.anonymous.beans.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author child
 * 2019/4/10 9:42
 * <p>
 * 执行 sql 工具类: 专门用于执行 sql 语句
 * 加载驱动
 * 建立连接
 * 执行 sql
 * 得到结果集
 * 非封装对象
 * 关闭资源
 */
public class Executor {
    private final Configuration configuration;

    public Executor(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * @param sql        需要执行的 sql 语句: sql 映射配置文件中的 sql 语句
     * @param resultType 查询 数据 对应的 JavaBean
     * @param <E>        对应 resultType 的 JavaBean
     * @return 封装好的 JavaBean 的集合
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public <E> List<E> executeQuery(String sql, String resultType) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        //加载驱动
        Class.forName(configuration.getDriver());
        //建立连接
        Connection connection = DriverManager.getConnection(configuration.getUrl(), configuration.getUsername(), configuration.getPassword());
        //预编译 sql: 得到 执行 sql 对象
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //执行 sql: 得到 结果集
        ResultSet resultSet = preparedStatement.executeQuery();

        //元数据：数据库有关，可以获取数据库查询结果的所有字段名
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount(); //字段数量

        //获取所有字段名
        List<String> columnNames = new ArrayList<String>();
        //字段索引从 1 开始! -- 列数
        for (int i = 1; i <= columnCount; i++) {
            //            System.out.println(metaData.getColumnName(i)); //测试
            columnNames.add(metaData.getColumnName(i));
        }

        //反射，创建对象，调用 set 方法
        Class clazz = Class.forName(resultType);
        Method[] methods = clazz.getDeclaredMethods();
        //封装对象
        List<E> list = new ArrayList<>();
        while (resultSet.next()) {
            //反射创建对象: 空参构造必须有
            E obj = (E) clazz.newInstance();
            //反射 set 方法，一边循环一边设置 -- 这也是 JavaBean 一般都必须要有 set 方法的原因
            for (String columnName : columnNames) { //字段名：对应 JavaBean 中 属性名
                for (Method method : methods) {
                    if (method.getName().equalsIgnoreCase("set" + columnName)) {
                        //用查到的数据 给 对应的字段赋值
                        method.invoke(obj, resultSet.getObject(columnName));
                    }
                }
            }
            //封装对象到集合
            list.add(obj);
        }
        //关闭资源
        connection.close();
        preparedStatement.close();
        resultSet.close();
        //返回 结果集 封装的 对象(JavaBean)集合
        return list;
    }
}
