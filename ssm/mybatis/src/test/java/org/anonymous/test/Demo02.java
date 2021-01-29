package org.anonymous.test;

import org.anonymous.dao.impl.ContactDaoImpl;
import org.anonymous.domain.Contact;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author child
 * 2019/4/10 22:07
 * 传统方式: 不适用动态代理,使用 mybatis 提供的方法
 */
public class Demo02 {
    private final Contact contact = new Contact();
    private SqlSessionFactory sqlSessionFactory;
    private ContactDaoImpl contactDao;

    @Before
    public void init() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
        contactDao = new ContactDaoImpl(sqlSessionFactory);
    }

    @Test //保存
    public void test() {

        contact.setName("小马哥");
        contact.setSex("男");
        contact.setAge(100);
        contact.setProvince_id(10);
        contact.setEmail("234535@qq.com");
        contact.setQq("43654654");

        contactDao.saveContact(contact);
    }

    @Test //更改
    public void test1() {
        contact.setName("大马");
        contact.setId(165);
        contactDao.updateContact(contact);
    }

    @Test //删除: 对象
    public void test2() {
        contact.setId(162);
        contactDao.deleteContact(contact);
    }

    @Test //删除 2: id
    public void test3() {
        contactDao.deleteContact0(163);
    }

    @Test //全查
    public void test4() {
        List<Contact> list = contactDao.findAll();
        list.forEach(System.out::println);
    }

    @Test //查单个
    public void test5() {
        Contact contact = contactDao.findById(166);
        System.out.println(contact);
    }

    @Test //模糊查询
    public void test6() {
        List<Contact> list = contactDao.findLike("%张%");
        list.forEach(System.out::println);
    }

    @Test //统计
    public void test7() {
        int count = contactDao.count();
        System.out.println(count);
    }
}
