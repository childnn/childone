package org.anonymous.test.annotation;

import org.anonymous.dao.annotation.UserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/8/24 17:40
 */
public class TestIn {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
    }

    /**
     * 测试注解 in 的使用
     */
    @Test
    public void test() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<Map<String, Object>> maps = mapper.selectIn("1, 2, 3");
        // 数据库的字段没有值, 就不会封装到 map.
        maps.forEach(System.out::println);
        Object sex = maps.get(2).get("sex");
        System.out.println("sex = " + sex); // null
        List<Map<String, Object>> maps1 = mapper.selectIn$(1, 2, 3);
        maps1.forEach(System.out::println);

        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        List<Map<String, Object>> maps2 = mapper.selectIn(ids.toString().replace("[", "").replace("]", ""));
        maps2.forEach(System.out::println);

        sqlSession.close();
    }

    @Test
    public void test1() {
        @SuppressWarnings("serial")
        List<Integer> ids = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }};
        StringBuilder sb = new StringBuilder();
        int size = ids.size();
        for (int i = 0; i < size; i++) {
            sb.append(ids.get(i));
            if (i < size - 1) {
                sb.append(",");
            }
        }
        System.out.println("sb = " + sb);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<Map<String, Object>> maps = mapper.selectIn(sb.toString());
        maps.forEach(System.out::println);

        sqlSession.close();
    }
}
