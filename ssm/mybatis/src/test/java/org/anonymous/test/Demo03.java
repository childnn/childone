package org.anonymous.test;

import org.anonymous.dao.ContactDao;
import org.anonymous.domain.Contact;
import org.anonymous.domain.PageBean;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author child
 * 2019/4/11 10:42
 * 根据对象查询的表示方式: 动态代理
 */
public class Demo03 {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
    }

    @Test
    public void test1() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ContactDao mapper = sqlSession.getMapper(ContactDao.class);
        PageBean pageBean = new PageBean();
        Contact contact = new Contact();
        contact.setId(4);
        pageBean.setContact(contact);
        Contact contact1 = mapper.findBC(pageBean);
        sqlSession.close();
        System.out.println(contact1);
    }
}
