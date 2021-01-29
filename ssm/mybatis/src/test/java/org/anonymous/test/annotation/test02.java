package org.anonymous.test.annotation;

import org.anonymous.dao.AccountDao;
import org.anonymous.dao.UserDao;
import org.anonymous.domain.Account;
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
 * 2019/4/12 21:36
 * 注解与映射, 延迟加载
 */
public class test02 {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
    }

    @Test //查询所有 用户信息: 没有关联账户 --> 关联映射: 延迟加载
    public void test1() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> list = mapper.findUser();
        //        list.forEach(System.out::println);
        for (User user : list) {
            System.out.println(user.getId());
            System.out.println(user.getAccounts()); //延迟加载
        }
        sqlSession.close();
    }

    @Test //根据 用户 id 查 账户(uid)
    public void test2() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AccountDao mapper = sqlSession.getMapper(AccountDao.class);
        List<Account> list = mapper.findAccountByUId(41);
        list.forEach(System.out::println);

        sqlSession.close();
    }
}
