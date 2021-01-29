package org.anonymous.test;

import org.anonymous.dao.UserDao;
import org.anonymous.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author child
 * 2019/4/12 18:16
 * mybatis 缓存机制
 * 一级缓存: sqlSession 级别的缓存
 * 二级缓存: sqlSessionFactory 级别的缓存
 */
public class Demo02_cache {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
    }

    @Test //一级缓存 生命周期
    public void test1() {
        SqlSession sqlSession = sqlSessionFactory.openSession(); //一级缓存开启

        sqlSession.close(); //一级缓存销毁
    }

    /**
     * //一级缓存的测试:
     * 第一次查询的时候 -> 去一级缓存查找 -> 没有 -> 去数据库中查找 -> 存入 一级缓存
     * *          第二次查询的时候 -> 一级缓存中查询 -> 有 -> 直接获取
     * 在同一个 sqlSession 生命周期中 多次查询 相同的信息, 则 第一次查询之后的 都在 一级缓存中 查询,不会 重新执行 sql
     */
    @Test
    public void test2() {
        SqlSession sqlSession = sqlSessionFactory.openSession(); //开启一级缓存
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user1 = mapper.findUserById(41);
        System.out.println("user1 = " + user1);
        User user2 = mapper.findUserById(41);
        System.out.println("user2 = " + user2);

        //如果注释了下面的语句: 不会将 前一个 sqlSession 中的数据写入 二级缓存 - 也就是说必须 提交/关闭 前一次的 sqlSession (一级缓存)
        //        sqlSession.commit(); //销毁一级缓存 //如果开启了二级缓存,则会将前一个 sqlSession 查询的数据(一级缓存) 写入 二级缓存
        sqlSession.close(); //销毁 一级缓存中信息 //如果开启了二级缓存,则将数据 写入 二级缓存

        //开启了二级缓存,下面的查询不会 重复执行 sql 语句 -- 多个 sqlSession 共用 二级缓存
        //没有开启,就会再次执行 sql
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        UserDao mapper1 = sqlSession1.getMapper(UserDao.class);
        User user3 = mapper1.findUserById(41);
        System.out.println("user3 = " + user3);

        sqlSession1.close();
    }
}
