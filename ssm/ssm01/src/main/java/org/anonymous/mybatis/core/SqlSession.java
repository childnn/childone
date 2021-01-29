package org.anonymous.mybatis.core;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author child
 * 2019/4/10 9:11
 * 操作数据库的接口
 */
public interface SqlSession {
    //queryForList
    <E> List<E> selectList(String key) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException, InvocationTargetException;

    // 返回 给定 接口的代理对象: 给定接口的字节码对象
    <E> E getMapper(Class<E> interfaceClass);
}
