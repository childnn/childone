package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import entity.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @see JSON#parseObject(String, Class)
 * @since 2019/9/7 23:11
 */
public class FastJsonTest {

    private static final List<Student> stus = new ArrayList<>();

    static {
        Student stu = new Student();
        stu.setAge(1);
        stu.setName("jack");
        Student stu1 = new Student();
        stu.setAge(2);
        stu.setName("jack1");
        Student stu2 = new Student();
        stu.setAge(3);
        stu.setName("jack2");
        stus.add(stu);
        stus.add(stu1);
        stus.add(stu2);
    }

    @Test
    public void parseObject() {
        Map map = JSON.parseObject("{key: 1, value: 2}", Map.class);
        System.out.println(map);
        JSONObject jsonObject = JSON.parseObject("{key: 1, value: 2}");
        System.out.println(jsonObject);
        // 属性名 equals key. -- 如果把 数字赋值给 string, 可以不加引号.
        Student student = JSON.parseObject("{name: 1, age: 2}", Student.class);
        System.out.println(student);
    }

    @Test
    public void parseObj() {
        // 字符串必须加引号. -- 否则就会报错.
        Student student = JSON.parseObject("{name: 'jack', age: 2, address: {city: 'harbin'}}", Student.class);
        System.out.println(student);
    }

    @Test
    public void parseObj1() {
        String s = JSON.toJSONString(stus.toArray(), true); // 美化输出.
        System.out.println(s);
        // JSON.parseObject("[]", Student.class);
    }

    @Test
    public void dateParse() {
        Date date = new Date();
        String s2 = JSON.toJSONString(date);
        System.out.println(s2);
        String s = JSON.toJSONString(date, SerializerFeature.WriteDateUseDateFormat);
        System.out.println(s);
        String replace = s.replace("\"", "");
        System.out.println(replace);
        String s1 = JSON.toJSONStringWithDateFormat(date, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
        System.out.println(s1);
    }

    @Test
    public void parseObj2() {
        Student stu = new Student();
        stu.setAge(1);
        stu.setName("jack");
        Student stu1 = new Student();
        stu.setAge(1);
        stu.setName("jack");
        Student[] stus = {
                stu, stu1
        };
        String s = JSON.toJSONString(stus);
        System.out.println(s);
    }

    @Test
    public void parse() {

    }

    @Test
    public void parseArray() {
        // 序列化.
        String s = JSON.toJSONString(new ArrayList<String>() {{
            add("1");
            add("1");
            add("1");
        }});
        System.out.println("s = " + s);
        // 反序列化.
        List<String> list = JSON.parseArray(s, String.class);
        System.out.println("list = " + list);
    }

    @Test
    public void test2() {
        JSONObject parse = ((JSONObject) JSON.parse("{name: 'jack', age: 2, address: {city: 'harbin'}}"));
        System.out.println(parse);
        JSONArray parse1 = (JSONArray) JSON.parse("[1, 2, 3]");
        System.out.println(parse1);
    }

    @Test
    public void test3() {

    }
}
