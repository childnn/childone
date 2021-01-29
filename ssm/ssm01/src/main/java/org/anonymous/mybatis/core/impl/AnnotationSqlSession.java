package org.anonymous.mybatis.core.impl;

import org.anonymous.beans.Configuration;
import org.anonymous.beans.Mapper;
import org.anonymous.mybatis.annotation.Select;
import org.anonymous.mybatis.core.SqlSession;
import org.anonymous.util.Executor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author child
 * 2019/4/10 9:12
 * 操作数据库： Configuration
 */
@SuppressWarnings("unchecked")
public class AnnotationSqlSession implements SqlSession {
    private final Configuration configuration;
    private final Executor executor;

    public AnnotationSqlSession(Configuration configuration) {
        this.configuration = configuration;
        executor = new Executor(configuration);
    }

    //封装
    @Override
    public <E> List<E> selectList(String key) throws ClassNotFoundException, SQLException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Map<String, Mapper> map = configuration.getMappers();
        Mapper mapper = map.get(key);
        String sql = mapper.getQuerySql();
        String resultType = mapper.getResultType();

        //工具类: 操作数据库,返回结果
        return executor.executeQuery(sql, resultType);
    }

    //产生 指定接口 的代理对象(接口实现类)
    @Override
    public <E> E getMapper(Class<E> interfaceClass) {
        Object proxyInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //获取 待启动(接口)方法的注解
                Select select = method.getAnnotation(Select.class);
                //获取注解上的属性值
                String sql = select.querySql();
                String resultType = select.resultType();

                System.out.println(String.format("注解被解析了: sql [%s], result type [%s]", sql, resultType));
                return null;

                //return executor.<E>executeQuery(sql, resultType); //返回值由 代理对象 调用方法后 接收
            }
        });
        return (E) proxyInstance; //返回 指定接口的 代理对象
    }
}
