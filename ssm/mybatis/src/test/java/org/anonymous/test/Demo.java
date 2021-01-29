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
 * 2019/4/10 19:31
 * 基本使用 crud
 */
public class Demo {
    private SqlSessionFactory sqlSessionFactory; //线程安全

    @Before
    public void init() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
    }

    @Test//插入
    public void test1() throws IOException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //代理对象
        ContactDao mapper = sqlSession.getMapper(ContactDao.class);
        //指定插入数据
        Contact contact = new Contact();
        contact.setName("老王");
        contact.setSex("男");
        contact.setAge(18);
        contact.setProvince_id(10);
        contact.setQq("4354354");
        contact.setEmail("435465@163.com");
        //启动方法
        mapper.saveContact(contact);

        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();

        //返回 插入数据后,最后一条记录的 id
        System.out.println(contact.getId());
    }

    @Test//修改
    public void test2() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ContactDao mapper = sqlSession.getMapper(ContactDao.class);
        //修改
        Contact contact = new Contact();
        contact.setId(1); //指定 要修改的 id
        contact.setName("老王"); //指定修改后的 name 字段 值
        //执行
        mapper.updateContact(contact);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }

    @Test //删除1
    public void test3() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ContactDao mapper = sqlSession.getMapper(ContactDao.class);

        //删除
        Contact contact = new Contact();
        contact.setId(1);
        //执行
        mapper.deleteContact(contact);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }

    @Test//删除2
    public void test4() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ContactDao mapper = sqlSession.getMapper(ContactDao.class);

        //删除
        mapper.deleteContact0(3);

        //提交事务
        sqlSession.commit();
        //关闭资源
        sqlSession.close();
    }

    @Test //查询全部
    public void test5() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ContactDao mapper = sqlSession.getMapper(ContactDao.class);
        List<Contact> list = mapper.findAll();
        //查询不需要提交事务
        //关闭资源
        sqlSession.close();
        list.forEach(System.out::println);
    }

    @Test //根据 id 查询
    public void test6() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ContactDao mapper = sqlSession.getMapper(ContactDao.class);
        Contact contact = mapper.findById(166);

        sqlSession.close();
        System.out.println(contact);
    }

    @Test //模糊查询: 占位符 -- preparedStatement 对象: 预编译
    public void test7() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ContactDao mapper = sqlSession.getMapper(ContactDao.class);
        List<Contact> list = mapper.findLike("%张%");

        sqlSession.close();
        list.forEach(System.out::println);
    }

    @Test //模糊查询: sql 拼接 -- statement: sql 注入风险
    public void test8() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ContactDao mapper = sqlSession.getMapper(ContactDao.class);
        List<Contact> list = mapper.findLike("张");

        sqlSession.close();
        list.forEach(System.out::println);
    }

    @Test
    public void test9() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ContactDao mapper = sqlSession.getMapper(ContactDao.class);
        int count = mapper.count();

        sqlSession.close();

        System.out.println(count);
    }
}
