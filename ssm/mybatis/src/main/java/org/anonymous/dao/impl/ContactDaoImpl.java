package org.anonymous.dao.impl;

import org.anonymous.dao.ContactDao;
import org.anonymous.domain.Contact;
import org.anonymous.domain.PageBean;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author child
 * 2019/4/10 21:58
 * 传统方式: 非动态代理的 mybatis
 */
public class ContactDaoImpl implements ContactDao {
    private final SqlSessionFactory sqlSessionFactory;

    public ContactDaoImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override//保存
    public void saveContact(Contact contact) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        /*
            参数1: namespace + "." + id
            参数2: 要保存的参数数据
         */
        sqlSession.insert("org.anonymous.dao.ContactDao.saveContact", contact);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }

    @Override //修改
    public void updateContact(Contact contact) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("org.anonymous.dao.ContactDao.updateContact", contact);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override //删除
    public void deleteContact(Contact contact) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("org.anonymous.dao.ContactDao.deleteContact", contact);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteContact0(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("org.anonymous.dao.ContactDao.deleteContact", id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override //全查
    public List<Contact> findAll() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Contact> list = sqlSession.selectList("org.anonymous.dao.ContactDao.findAll");
        sqlSession.close();
        return list;
    }

    @Override //查单个
    public Contact findById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Object obj = sqlSession.selectOne("org.anonymous.dao.ContactDao.findById", id);
        sqlSession.close();
        return (Contact) obj;
    }

    @Override //模糊查询
    public List<Contact> findLike(String name) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Contact> list = sqlSession.selectList("org.anonymous.dao.ContactDao.findLike", name);
        sqlSession.close();
        return list;
    }

    @Override //统计
    public int count() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int count = sqlSession.selectOne("org.anonymous.dao.ContactDao.count");
        sqlSession.close();
        return count;
    }

    @Override
    public List<Contact> find() {
        return null;
    }

    @Override
    public Contact findBC(PageBean pageBean) {
        return null;
    }
}
