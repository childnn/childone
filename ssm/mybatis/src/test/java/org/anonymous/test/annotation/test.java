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
 * 2019/4/12 20:08
 * 注解的基本使用: crud
 */
public class test {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
    }

    @Test //保存
    public void test1() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setUsername("jack");
        user.setBirthday("1999-9-9");
        user.setSex("男");
        user.setAddress(null);
        mapper.saveUser(user);

        sqlSession.commit();
        sqlSession.close();

        System.out.println(user.getId());
    }

    @Test //update
    public void test2() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setId(51);
        user.setUsername("小老王");
        mapper.updateUser(user);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test //根据 id 删除
    public void test3() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        mapper.deleteUserById(51);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test //根据 id 查
    public void test4() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user = mapper.findById(50);
        System.out.println(user);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test //模糊查: el 表达式形式
    public void test5() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> list = mapper.findLike("王");
        list.forEach(System.out::println);

        sqlSession.close();
    }

    @Test //模糊查询2: ognl 表达式
    public void test6() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> list = mapper.findLike1("%王%");
        for (User user : list) {
            System.out.println(user);
        }

        sqlSession.close();
    }

    @Test
    public void test7() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        int count = mapper.count();
        System.out.println(count);

        sqlSession.close();
    }
}
