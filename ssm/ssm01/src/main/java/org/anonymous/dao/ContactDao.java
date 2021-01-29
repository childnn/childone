package org.anonymous.dao;

import org.anonymous.mybatis.annotation.Select;
import org.dom4j.DocumentException;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author child
 * 2019/4/10 10:33
 */
public interface ContactDao {
    <E> List<E> findAll() throws ClassNotFoundException, SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, DocumentException;

    //查询
    @Select(querySql = "select * from contact", resultType = "org.anonymous.domain.Contact")
    default <E> List<E> findAll$() throws ClassNotFoundException, SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, DocumentException {
        return null;
    }
}
