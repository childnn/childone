package org.anonymous.test.mybatis;

import org.anonymous.beans.Contact;
import org.anonymous.dao.ContactDao;
import org.anonymous.mybatis.core.SqlSession;
import org.anonymous.mybatis.core.SqlSessionFactory;
import org.anonymous.mybatis.core.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author child
 * 2019/4/9 21:37
 * 自定义框架测试2:
 * 基于动态代理的 框架: 不需要 dao 接口的实现类 -- 在运行过程中,动态生成接口实现类对象
 * 目标对象： 接口的实现类
 * 代理对象： 动态代理实现接口,得到接口实现类
 */
public class Demo {
    @Test
    public void test1() throws DocumentException, ClassNotFoundException, SQLException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //测试自定义框架
        //封装 dao
        /*//创建 SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        //创建 SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml"));
        //获取 SqlSession
        SqlSession sqlSession = sqlSessionFactory.open();
        //使用 SqlSession: 操作数据库
        List<Contact> list = sqlSession.selectList("user.getAll");*/

        //调用 dao
        //        ContactDao contactDao = new ContactDaoImpl();

        //取代 dao 接口 的实现类
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(ClassLoader.getSystemResourceAsStream("mybatis/SqlMapConfig.xml"));
        SqlSession sqlSession = sqlSessionFactory.open(false);
        //返回 dao 层 接口 的 代理对象
        ContactDao contactDao = sqlSession.getMapper(ContactDao.class);
        List<Contact> list = contactDao.findAll(); // invoke 方法执行: 代理对象
        /*for (Contact user : list) {
            System.out.println(user);
        }*/
    }


    @Test
    public void test2() throws DocumentException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, InvocationTargetException {
        //取代 dao 接口 的实现类
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(ClassLoader.getSystemResourceAsStream("mybatis/SqlMapConfig.xml"));
        SqlSession sqlSession = sqlSessionFactory.open(true);
        ContactDao mapper = sqlSession.getMapper(ContactDao.class);
        mapper.findAll$();
    }
}
