package org.anonymous.mybatis.core.impl;

import org.anonymous.beans.Configuration;
import org.anonymous.beans.Mapper;
import org.anonymous.mybatis.core.SqlSession;
import org.anonymous.util.Executor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author child
 * 2019/4/10 9:12
 * 操作数据库： Configuration
 */
@SuppressWarnings("all")
public class DefaultSqlSession implements SqlSession {
    //private final Configuration configuration;
    private final Executor executor;
    private final Map<String, Mapper> mapperMap = new ConcurrentHashMap<>();

    public DefaultSqlSession(Configuration configuration) {
        //this.configuration = configuration;
        mapperMap.putAll(configuration.getMappers());
        //创建 sql 执行工具类对象: 传递 数据库连接核心配置文件对象
        executor = new Executor(configuration);
    }


    //产生 指定接口 的代理对象(接口实现类)
    @Override
    public <E> E getMapper(Class<E> interfaceClass) {
        Object proxyInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass}, (proxy, method, args) -> {
                    //业务逻辑
                    System.out.println("代理对象调用任何方法,我都执行!");
                    //直接调用 selectList(String key)
                    /*
                     * key: namespace.id
                     *      namespace: 当前接口的全限定名: org.anonymous.dao.ContactDao
                     *      id: 方法名 -- 代理对象调用的方法的方法名 --  method.getName()
                     */
                    Type[] genericInterfaces = proxy.getClass().getGenericInterfaces();
                    //                System.out.println(Arrays.toString(genericInterfaces));
                    String namespace = genericInterfaces[0].getTypeName();
                    String id = method.getName();
                    String key = namespace + "." + id;
                    //                System.out.println(key);
                    return selectList(key); //返回值由 代理对象 调用方法后 接收
                });
        return (E) proxyInstance; //返回 指定接口的 代理对象
    }

    //封装对象到list
    public <E> List<E> selectList(String key) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException, InvocationTargetException {
        //Map<String, Mapper> map = configuration.getMappers();
        //根据 key 获取 指定 的 value : map
        Mapper mapper = mapperMap.get(key);
        //获取 Mapper 对象的 属性值
        String sql = mapper.getQuerySql();
        String resultType = mapper.getResultType();
        System.out.println("sql = " + sql);
        //工具类
        //List<E> list = executor.executeQuery(sql, resultType);

        return null;

        //封装工具类
        /*//加载驱动
        Class.forName(configuration.getDriver());
        Connection connection = DriverManager.getConnection(configuration.getUrl(), configuration.getUsername(), configuration.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        //元数据：数据库有关，可以获取数据库查询结果的所有字段名
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount(); //字段数量

        //获取所有字段名
        List<String> columnNames = new ArrayList<String>();
        for (int i = 1; i < columnCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }

        //反射，创建对象，调用 set 方法
        Class clazz = Class.forName(resultType);
        Method[] methods = clazz.getDeclaredMethods();
        //封装对象
        List list = new ArrayList();
        while (resultSet.next()) {
            Object obj = clazz.newInstance();
            //反射 set 方法，一边循环一边设置
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
        }*/
        //return list;
    }
}
