package jackson.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/10/19
 */
public class GenericSeria {

    static ObjectMapper mapper = new ObjectMapper();

    // static boolean autoConfig = true;

    private static void autoConfig(boolean autoConfig) {
        if (autoConfig) {
            mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        /*
            该配置在序列化时会保留全限定名, 不是如 fastjson 的序列化方式
            在反序列化时可以直接强转为相应的对象(包括带有范型的对象)
            示例: 序列化结果为数组形式- 类名+属性
            [
              "com.rsy.common.springmvc.ResultModel", // 类名
                  {
                    "title": "",
                    "success": true,
                    "code": 1,
                    "msg": "",
                    "data": [
                      "com.rsy.rxf.dto.security.AuthCodeDTO", // 类名
                      {
                        "key": "fasdfsa",
                        "img": "fdafa"
                      }
                    ]
                  }
            ]
            DefaultTyping有四个选项：
                JAVA_LANG_OBJECT: 当对象属性类型为Object时生效；
                OBJECT_AND_NON_CONCRETE: 当对象属性类型为Object或者非具体类型（抽象类和接口）时生效；
                NON_CONCRETE_AND+_ARRAYS: 同上, 另外所有的数组元素的类型都是非具体类型或者对象类型；
                NON_FINAL: 对所有非final类型或者非final类型元素的数组。
         */
            mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
            // mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL); // 低版本
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        }
    }

    @Test
    public void test2() throws JsonProcessingException {
        autoConfig(true);
        // R<X> xr = new R<>();
        // xr.setName("jack");
        // xr.setId("aaa");
        // X x = new X();
        // x.setDate(new Date());
        // x.setSex(1);
        // xr.setData(x);

        Map<String, Object> x = new HashMap<>();
        x.put("date", new Date());
        x.put("sex", 1);

        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        List<Map<String, Object>> xs = new ArrayList<>();
        xs.add(x);
        map.put("aaa", xs);

        String s = mapper.writeValueAsString(map);
        System.out.println(s);

        // TypeFactory typeFactory = mapper.getTypeFactory();
        // JavaType simpleType = typeFactory.constructSimpleType(String.class, null);
        // JavaType listType = typeFactory.constructParametricType(List.class, X.class);
        //
        // JavaType javaType = typeFactory.constructParametricType(Map.class, simpleType, listType);
        // Object o = mapper.readValue(s, javaType);
        // System.out.println("o = " + o);
    }

    @Test
    public void test() throws JsonProcessingException {
        autoConfig(true);
        R<X> xr = new R<>();
        xr.setName("jack");
        xr.setId("aaa");
        X x = new X();
        x.setDate(new Date());
        x.setSex(1);
        xr.setData(x);

        String x1 = mapper.writeValueAsString(xr);
        // ["jackson.test.GenericSeria$R",{"id":"aaa","name":"jack","data":["jackson.test.GenericSeria$X",{"date":["java.util.Date","2022-10-19 16:51:33"],"sex":1}]}]
        System.out.println(x1);

        xr = mapper.readValue(x1, mapper.getTypeFactory()
                .constructParametricType(R.class, Object.class));
        System.out.println("xr = " + xr);

    }

    @Test
    public void test1() throws JsonProcessingException {
        // autoConfig = false;
        R<X> xr = new R<>();
        xr.setName("jack");
        xr.setId("aaa");
        X x = new X();
        x.setDate(new Date());
        x.setSex(1);
        xr.setData(x);

        String x1 = mapper.writeValueAsString(xr);
        // {"id":"aaa","name":"jack","data":{"date":1666169473400,"sex":1}}
        System.out.println(x1);

        xr = mapper.readValue(x1, mapper.getTypeFactory()
                .constructParametricType(R.class, X.class));
        System.out.println("xr = " + xr);

        xr = mapper.readValue(x1, new TypeReference<R<X>>() {
        });
        System.out.println("xr = " + xr);

    }


    @Data
    static class R<T> {
        private String id;
        private String name;
        private T data;
    }

    @Data
    static class X {
        private Date date;
        private Integer sex;
    }

}

