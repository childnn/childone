package org.anonymous.dao.impl;

import org.anonymous.dao.ContactDao;
import org.anonymous.mybatis.core.SqlSession;
import org.anonymous.mybatis.core.SqlSessionFactory;
import org.anonymous.mybatis.core.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author child
 * 2019/4/10 10:34
 * 传统方式:基于接口实现类
 */
public class ContactDaoImpl implements ContactDao {

    public <E> List<E> findAll() throws ClassNotFoundException, SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, DocumentException {
        //创建 SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        //创建 SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml"));
        //获取 SqlSession
        SqlSession sqlSession = sqlSessionFactory.open(false);
        //使用 SqlSession: 操作数据库 -- 传递 key 值 namespace.id
        List<E> list = sqlSession.selectList("user.getAll");
        return null;
    }
}
