package org.anonymous.test.annotation;

import org.anonymous.dao.annotation.UserDao;
import org.anonymous.domain.User;
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
 * 2019/4/12 21:24
 * 注解与映射: 别名映射
 */
public class test01 {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
    }

    @Test
    public void test1() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> list = mapper.findAll();
        list.forEach(System.out::println);

        sqlSession.close();
    }
}
