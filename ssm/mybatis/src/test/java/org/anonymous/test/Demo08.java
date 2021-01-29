package org.anonymous.test;

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
 * 2019/4/12 17:17
 * 延迟加载2:
 * 以用户为基准的 查询: 1 对多的查询方式
 * 主查询: select * from user
 * 从查询: select * from account where uid = #{随便写};
 */
public class Demo08 {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
    }

    @Test //查询所有 用户: 没有关联账户,账户没有信息//开启延迟加载,引入 sql
    public void test1() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> list = mapper.findUsers();
        for (User user : list) {
            System.out.println(user.getId());
            //延迟加载:
            System.out.println(user.getAccounts());
        }

        sqlSession.close();
    }

    @Test //根据用户 id 查询该 id 下的所有账户: user 没有关联
    public void test2() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AccountDao mapper = sqlSession.getMapper(AccountDao.class);
        List<Account> list = mapper.findAccountByUId(41);
        for (Account account : list) {
            System.out.println(account);
        }

        sqlSession.close();
    }
}
