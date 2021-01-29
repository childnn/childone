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
 * 2019/4/12 13:34
 * mybatis 与 延迟加载(懒加载)
 * 以 account 为基准的 查询: 1 对 1 的查询方式(表关系是一对多)
 * 主查询: select * from account
 * 从查询(延迟查询): select * from user where
 */
public class Demo07 {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
    }

    //左外: 查询量表所有信息
    @Test //多表: 以 account 为基准: 一个 account 只会对应一个 user  -- 查询账户下的所有用户信息(两张表的所有信息)
    public void test1() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AccountDao mapper = sqlSession.getMapper(AccountDao.class);
        List<Account> list = mapper.findList();
        list.forEach(System.out::println);

        sqlSession.close();
    }

    /**
     * 在多表查询中, 如果只想查询 多表之中 某一部分信息,而非全部信息: 配置文件中的 sql 如果还是写 全部查询的 sql(如左外查询)
     * 此时就会浪费效率/资源.
     * 那么,现在希望, 如果没用到 account 中关联的 user 对象(表) 中的数据, 就只查 account 数据
     * 什么时候要用到 user 的相关数据, 在去查询, 以提高效率
     * 此时,就要用到 "延迟加载技术"
     */
    @Test //延迟加载初步: 1
    public void test2() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AccountDao mapper = sqlSession.getMapper(AccountDao.class);
        List<Account> list = mapper.findAccount();
        for (Account account : list) {
            System.out.println(account.getUid()); //账户关联的用户的 id
            //下面这一句表示用到 account 关联的 user : 如果将其注释掉, 则不会 引入关联(不会查询 user), 如果不注释,则会去 查询 user
            System.out.println(account.getUser()); //账户关联的用户: 未关联查询不到 //引入 org/anonymous/dao/UserDao.xml 之后查到
        }
        sqlSession.close();
    }

    @Test //延迟加载: 2
    public void test3() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User userById = mapper.findUserById(41);
        System.out.println(userById);

        sqlSession.close();
    }
}
