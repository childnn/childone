package org.anonymous.test;

import org.anonymous.dao.ContactDao;
import org.anonymous.domain.Contact;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author child
 * 2019/4/10 21:35
 * 映射
 */
public class Demo01 {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
    }

    @Test//映射
    public void test1() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ContactDao mapper = sqlSession.getMapper(ContactDao.class);
        List<Contact> list = mapper.find();

        sqlSession.close();

        list.forEach(System.out::println);
    }


}
