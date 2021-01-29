package org.anonymous.service.impl;

import org.anonymous.beans.Admin;
import org.anonymous.beans.Contact;
import org.anonymous.beans.PageBean;
import org.anonymous.beans.PageBean1;
import org.anonymous.dao.AbstractContactDao;
import org.anonymous.service.ContactService;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @author child
 * 2019/3/29 11:41
 * 逻辑:
 * 事务: 查询不需要考虑事务
 * 增删改需要考虑事务: 需要多条 sql 同时成功
 */
public class ContactServiceImpl implements ContactService {
    public static List<Contact> getContact() {
        return AbstractContactDao.getContact();
    }

    public static Admin check(String username, String password) {
        return AbstractContactDao.check(username, password);
    }

    public static void create(Contact contact) {
        AbstractContactDao.create(contact);
    }

    public static Contact getId(String id) {
        return AbstractContactDao.getId(id);
    }

    public static void update(Contact contact) {
        AbstractContactDao.update(contact);
    }

    public static void delete(String id) {
        AbstractContactDao.delete(id);
    }

    public static void deleteAll() {
        AbstractContactDao.deleteAll();
    }

    public static void deleteSelected(Enumeration<String> enumeration) {
        //这里把 循环放在了 dao 层: 一般不推荐这么做 -- dao 层只用来和数据库做交互,逻辑代码 写在 service 层
        //        ContactDao.deleteSelected(enumeration);
        //把 循环 放在 service 层
        //批量删除,同时执行多条 sql, 需要写事务: 用到 spring 的事务管理系统
        while (enumeration.hasMoreElements()) {
            AbstractContactDao.delete(enumeration.nextElement());
        }
    }
    //条件查询
   /* public static void find(Enumeration<String> names) {
        ArrayList<String> list = new ArrayList<>();
        while (names.hasMoreElements()) {
            list.add(names.nextElement());
        }
        ListIterator<String> listIterator = list.listIterator();
        ContactDao.find(names);

    }*/

    /**
     * 当前页的数据: list
     * * 当前页 : pageNumber
     * * 总条数 : totalCount
     * * 总页数 : totalPages
     * * 每页要显示的条数 : pageSize
     *
     * @param pageNumber
     * @param pageSize
     * @param map
     * @return
     */
    public static PageBean pages(int pageNumber, int pageSize, Map<String, String[]> map) {
        //当前页起始编号
        int startIndex = (pageNumber - 1) * pageSize;
        //总页数
        int totalCount = AbstractContactDao.count(map);
        int totalPages = 0;
        //总记录数 模 每页的记录数,不为 0
        if (totalCount % pageSize != 0) {
            totalPages = totalCount / pageSize + 1;
        } else { //刚好整除
            totalPages = totalCount / pageSize;
        }
        //当前页数据: list
        List<Contact> list = AbstractContactDao.pages(startIndex, pageSize, map);
        return new PageBean(list, pageNumber, totalCount, totalPages, pageSize);
    }

    public static PageBean page(int pageNumber, int pageSize, String name, String qq) {
        //总条数
        int totalCount = AbstractContactDao.totalCount(name, qq);
        //总页数
        int totalPages = 0;
        if (totalCount % pageSize != 0) {
            totalPages = totalCount / pageSize + 1;
        } else {
            totalPages = totalCount / pageSize;
        }
        //        System.out.println(totalPages); //测试
        //当前页起始索引
        int startIndex = (pageNumber - 1) * pageSize;
        //当前页数据
        List<Contact> list = AbstractContactDao.find(startIndex, pageSize, name, qq);
        //        System.out.println(list); //测试
        //封装 PageBean
        return new PageBean(list, pageNumber, totalCount, totalPages, pageSize);
    }

    @Override
    public Admin checkAdmin(Admin admin) {
        return null;
    }

    @Override
    public PageBean1 pages(Contact contact, Integer pageNumber, Integer pageSize) {
        return null;
    }
}
