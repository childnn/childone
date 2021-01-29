package org.anonymous.test;

import org.anonymous.dao.ContactDao0;
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
 * 2019/4/11 11:04
 * 映射相关测试
 */
public class Demo04 {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
    }

    @Test
    public void test1() {
        //默认是 手动事务: 可以在创建连接的时候开启自动事务
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            ContactDao0 mapper = sqlSession.getMapper(ContactDao0.class);
            List<Contact> list = mapper.mapperContact();
            list.forEach(System.out::println);
        } //这个 try 就等价于 try..finally {sqlSession.close()}
    }
}
