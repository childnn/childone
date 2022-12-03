package org.anonymous.dao;

import org.anonymous.beans.Admin;
import org.anonymous.beans.Contact;
import org.anonymous.beans.SqlBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author child
 * 2019/4/1 18:30
 */
public class ContactDao1 {
    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(null/*JdbcTemplateUtils.getDataSource()*/);

    //校验用户名,密码
    public static Admin checkAdmin(Admin admin) {
        String sql = "select * from admin where username = ? and password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(admin.getClass()), admin.getUsername(), admin.getPassword());
        } catch (DataAccessException e) {
            //            e.printStackTrace(); //测试
            return null;
        }
    }

    //获取检索的数据总条数
    public static int getCount(Contact contact) {
        List<Object> list = new ArrayList<>();
        String sql = "select count(*) from contact where true";
        SqlBean sqlBean = getSQL(sql, contact, list);

        //        System.out.println(sqlBean.getSql()); //测试
        //        System.out.println("数据总条数:" + list);

        try {
            return jdbcTemplate.queryForObject(sqlBean.getSql(), int.class, sqlBean.getList().toArray());
        } catch (DataAccessException e) {
            e.printStackTrace(); //测试
            return 0;
        }
    }

    //获取检索的数据信息
    public static List<Contact> beanList(Contact contact, Integer index, Integer pageSize) {
        List<Object> list = new ArrayList<>();
        String sql = "select * from contact where true";
        //当前页起始索引，每页数据条数
        String sqlLimit = " limit ?, ?";
        SqlBean sqlBean = getSQL(sql, contact, list);
        List<Object> sqlBeanList = sqlBean.getList();
        sqlBeanList.add(index); //当前页起始索引
        sqlBeanList.add(pageSize); //每页显示的数据条数

        //        System.out.println(sqlBean.getSql() + sqlLimit); //测试
        //        System.out.println("当前页:" + list);

        try {
            return jdbcTemplate.query(sqlBean.getSql() + sqlLimit,
                    new BeanPropertyRowMapper<>(Contact.class), sqlBeanList.toArray());
        } catch (DataAccessException e) {
            //            e.printStackTrace();
            return null;
        }
    }

    //sql 工具方法
    private static SqlBean getSQL(String sql, Contact contact, List<Object> list) {
        Integer id = contact.getId();
        String name = contact.getName();
        String sex = contact.getSex();
        Integer age = contact.getAge();
        Integer province_id = contact.getProvince_id();
        String qq = contact.getQq();
        String email = contact.getEmail();

        //        String sql = "select * from contact where true";
        StringBuilder sb = new StringBuilder(sql);
        if (null != name && name.length() > 0) {
            sb.append(" and name like ?");
            list.add("%" + name + "%");
        }
        if (null != sex && sex.length() > 0) {
            sb.append(" and sex like ?");
            list.add("%" + sex + "%");
        }
        if (null != age && 0 != age) {
            sb.append(" and age like ?");
            list.add("%" + age + "%");
        }
        if (null != province_id && province_id > 0 && province_id < 35) {
            sb.append(" and province_id = ?");
            list.add(province_id);
        }
        if (null != qq && qq.length() > 0) {
            sb.append(" and qq like ?");
            list.add("%" + qq + "%");
        }
        if (null != email && email.length() > 0) {
            sb.append(" and email like ?");
            list.add("%" + email + "%");
        }
        SqlBean sqlBean = new SqlBean();
        sqlBean.setSql(sb.toString());
        sqlBean.setList(list);
        return sqlBean;
    }
}
