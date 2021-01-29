package org.anonymous.dao;

import org.anonymous.beans.Admin;
import org.anonymous.beans.Contact;
import org.anonymous.util.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author child
 * 2019/3/29 11:41
 */
public abstract class AbstractContactDao {
    private final static JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
    private static final ArrayList<Object> listCount = new ArrayList<>(); //计数
    private static final ArrayList<Object> listPages = new ArrayList<>(); //记录

    //到数据库查询所有 联系人 的信息,返回给 service 层
    public static List<Contact> getContact() {
        return jdbcTemplate.query("select * from contact", new BeanPropertyRowMapper<>(Contact.class));
    }

    //到数据库匹配用户名和密码: 有对应的用户(登录成功),返回对应的用户对象; 没有对应的用户(登录失败),返回 null
    public static Admin check(String username, String password) {
        try {
            return jdbcTemplate.queryForObject("select * from admin where username = ? and password = ?",
                    new BeanPropertyRowMapper<>(Admin.class), username, password);
        } catch (DataAccessException e) {
            return null;
        }
    }

    //往数据库添加数据
    public static void create(Contact contact) {
        int update = jdbcTemplate.update("insert into contact values(null, ?, ?, ?, ?, ?, ?)",
                contact.getName(), contact.getSex(), contact.getAge(), contact.getProvince_id(), contact.getQq(), contact.getEmail());
        //        System.out.println(update + "条数据添加成功!"); //测试
    }

    //拿着 update 给的 id 去数据库查询 到对象,并返回
    public static Contact getId(String id) {
        return jdbcTemplate.queryForObject("select * from contact where id = ?", new BeanPropertyRowMapper<>(Contact.class), id);
    }

    public static void update(Contact contact) {
        int update = jdbcTemplate.update("update contact set name = ?, sex = ?, age = ?, city = ?, qq = ?, email = ? where id = ?",
                contact.getName(), contact.getSex(), contact.getAge(), contact.getProvince_id(), contact.getQq(), contact.getEmail(), contact.getId());
        //        System.out.println("修改:" + update); //测试: 输出数据库中修改的记录条数
    }

    public static void delete(String id) {
        int update = jdbcTemplate.update("delete from contact where id = ?", id);
        //        System.out.println("删除:" + update);
    }

    public static void deleteAll() {
        int count = jdbcTemplate.update("truncate table contact");
        //        System.out.println("删除总记录:" + count);
    }

    @Deprecated //一般来说,逻辑代码 不会 放在 dao 执行
    public static void deleteSelected(Enumeration<String> enumeration) {
        int num = 0;
        while (enumeration.hasMoreElements()) {
            int update = jdbcTemplate.update("delete from contact where id = ?", enumeration.nextElement());
            num++;
        }
        //        System.out.println("已删除:" + num);
    }

    //计数查询记录总条数
    public static int count(Map<String, String[]> map) {
        String sql = "select count(*) from contact where 0 = 0";
        String s = get(map, sql).toString();
        System.out.println(s);
        System.out.println(listCount);
        try {
            return jdbcTemplate.queryForObject(s, int.class, listCount.toArray());
        } catch (DataAccessException e) {
            return 0;
        }
    }

    //分页:
    public static List<Contact> pages(int startIndex, int pageSize, Map<String, String[]> map) {
        String sql = "select * from contact where 0 = 0";
        String s = get(map, sql).append(" limit ?, ?").toString();
        System.out.println(s);
        listPages.add(startIndex);
        listPages.add(pageSize);
        System.out.println(listPages);

        try {
            return jdbcTemplate.query(s, new BeanPropertyRowMapper<>(Contact.class), listPages.toArray());
        } catch (DataAccessException e) {
            return null;
        }
    }

    //遍历 map 并判断 传过来的数据 : sql 拼接
    //后期有 更多的 查询条目, 可以直接往后拼接,便于维护
    private static StringBuilder get(Map<String, String[]> map, String sql) {
        StringBuilder sb = new StringBuilder(sql);
        String s = " and ";

        Set<String> set = map.keySet();
        for (String key : set) {
            String value = map.get(key)[0];
            if (value != null && value.trim().length() > 0 && Objects.equals(key, "name")) {
                sb.append(s).append("name like ?");
                listCount.add("%" + value + "%");
                listPages.add("%" + value + "%");
            }
            if (value != null && value.trim().length() > 0 && Objects.equals(key, "sex")) {
                sb.append(s).append("sex like ?");
                listCount.add("%" + value + "%");
                listPages.add("%" + value + "%");
            }
            if (value != null && value.trim().length() > 0 && Objects.equals(key, "age")) {
                sb.append(s).append("age like ?");
                listCount.add("%" + value + "%");
                listPages.add("%" + value + "%");
            }
            if (value != null && value.trim().length() > 0 && Objects.equals(key, "city")) {
                sb.append(s).append("city like ?");
                listCount.add("%" + value + "%");
                listPages.add("%" + value + "%");
            }
            if (value != null && value.trim().length() > 0 && Objects.equals(key, "qq")) {
                sb.append(s).append("email like ?");
                listCount.add("%" + value + "%");
                listPages.add("%" + value + "%");
            }
        }
        return sb;
    }

    //查询记录总条数
    public static int totalCount(String name, String qq) {
        String sql = "select count(*) from contact where 0 = 0";
        ArrayList<Object> list = new ArrayList<>();
        //判断传过来的数据是否 为 空,是否为 null
        if (!"".equals(name) && !Objects.equals(null, name)) {
            sql += " and name like ?";
            list.add("%" + name + "%");
        }
        if (!"".equals(qq) && !Objects.equals(null, qq)) {
            sql += " and qq like ?";
            list.add("%" + qq + "%");
        }

        return jdbcTemplate.queryForObject(sql, int.class, list.toArray());
    }

    //查询数据信息: 一条数据一个对象
    public static List<Contact> find(int startIndex, int pageSize, String name, String qq) {
        String sql = "select * from contact where 0 = 0";
        ArrayList<Object> list = new ArrayList<>();
        //判断传过来的数据是否 为 空,是否为 null
        if (!"".equals(name) && !Objects.equals(null, name)) {
            sql += " and name like ?";
            list.add("%" + name + "%");
        }
        if (!"".equals(qq) && !Objects.equals(null, qq)) {
            sql += " and qq like ?";
            list.add("%" + qq + "%");
        }
        sql += " limit ?, ?";
        //        System.out.println(sql);
        list.add(startIndex);
        list.add(pageSize);
        //        System.out.println(list); //测试
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Contact.class), list.toArray());
        } catch (DataAccessException e) {
            //            e.printStackTrace();
            return null;
        }
    }
}
