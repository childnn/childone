package test;

import beans.cglib.CglibBeanUtil;
import entity.Person;
import entity.Person1;
import entity.Stu;
import entity.Stu1;
import net.sf.cglib.core.DebuggingClassWriter;
import org.junit.jupiter.api.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/6/30 9:37
 */
public class BeanTest {

    @Test
    void test() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(String.class);
        System.out.println("beanInfo = " + beanInfo);
    }

    @Test
    void gbk() {
        System.out.println(new String("杩炴帴鎴愬姛".getBytes(Charset.forName("gbk")), StandardCharsets.UTF_8));
    }

    @Test
    void join() {
        String collect = Collections.<String>emptyList().stream().collect(Collectors.joining(","));
        System.out.println("collect = " + collect);
    }


    @SuppressWarnings("serial")
    public static void main(String[] args) {

        int[] arr = {1, 2, 3};// (int[]) (Arrays.asList().toArray());
        // 生成字节码到指定目录
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "E:\\dev-code\\WorkSpace\\child\\utils\\src\\main\\resources");

        System.out.println("============= copy bean ===============");
        Stu child = new Stu(111, "child", true, BigDecimal.valueOf(1321.213123), arr);
        Person person = CglibBeanUtil.copy(child, Person.class);
        System.out.println("person = " + person);

        System.out.println("============= copy bean list ===============");
        List<Person> people = CglibBeanUtil.copyBeanCollection(Arrays.asList(child, child, child), Person.class, "age");
        System.out.println("people = " + people);

        System.out.println("============= bean2map =================");
        Stu stu = new Stu();
        stu.setAge(12);
        stu.setName("jack");
        stu.setDef(true);
        Map<String, Object> map = CglibBeanUtil.bean2Map(stu);
        map.forEach((k, v) -> System.out.printf("k: %s -- v: %s%n", k, v));

        System.out.println("============= map2bean ===============");
        Stu1 bean = CglibBeanUtil.map2Bean(map, Stu1.class);
        System.out.println("bean = " + bean);
        System.out.println("bean class = " + bean.getClass());

        System.out.println("============= objs2maps ===============");
        List<Stu> list = Arrays.asList(
                new Stu(13, "jack", true, BigDecimal.valueOf(1321.213123), arr),
                new Stu(16, "rose", true, BigDecimal.valueOf(1321.213123), arr)
        );
        List<Map<String, Object>> maps = CglibBeanUtil.objectsToMaps(list);
        maps.forEach(m -> m.forEach((k, v) -> System.out.println(k + ": " + v)));

        System.out.println("============== maps2objs ==============");
        List<Stu1> stus = CglibBeanUtil.maps2Objects(maps, Stu1.class);
        stus.forEach(System.out::println);

        System.out.println("---------- 不同类型copy ----------------------");

        System.out.println("---------- foo to bar----------------------");

        Stu foo = new Stu(1, "2", true, BigDecimal.valueOf(1321.213123), arr);
        Stu1 bar = CglibBeanUtil.copy(foo, Stu1.class);
        System.out.println("bar = " + bar);


        System.out.println("---------- 不同类型 copy list----------------------");

        Person p = new Person();
        p.setAge(1);
        p.setName("JACK");
        p.setChildren(new ArrayList<Stu>() {
            {
                add(new Stu(2, "rose", false, BigDecimal.valueOf(13213L), arr));
                add(new Stu(3, "rose", true, BigDecimal.valueOf(312312L), arr));
                add(new Stu(4, "rose", true, BigDecimal.valueOf(43534L), arr));
            }
        });
        p.setStrs(new ArrayList<String>() {{
            add("1");
            add("2");
        }});

        p.setStu(new Stu(33, "Rose", true, BigDecimal.valueOf(41321L), arr));
        Person1 copy = CglibBeanUtil.copy(p, Person1.class, /*"age", */true, "name");
        System.out.println("copy = " + copy);
        Stu1 stu11 = copy.getChildren().get(0);
        System.out.println(stu11.getClass()); // 如果没有转换类型: java.lang.ClassCastException
        // System.out.println("copy.getChildren() == p.getChildren() : " + (copy.getChildren() == p.getChildren()));
        System.out.println(copy.getStu() == p.getStu());
        Stu stu1 = p.getChildren().get(0);
        System.out.println(stu11.getBigDecimal() == stu1.getBigDecimal());
        copy.getChildren().get(0).setBigDecimal(BigDecimal.valueOf(312312.31213)); // new instance
        System.out.println(stu11.getBigDecimal() == stu1.getBigDecimal());

        int[] arr1 = stu1.getArr();
        int[] arr2 = stu11.getArr();
        System.out.println(arr1 == arr2);
        arr1[1] = 333;
        System.out.println(arr1 == arr2);

        List<String> strs = p.getStrs();
        Collection<String> strs1 = copy.getStrs();
        System.out.println("strs1 = " + strs1);
        System.out.println(strs == strs1);
        strs.add("132");
        System.out.println("strs1 = " + strs1);
        System.out.println("strs = " + strs);
    }

}
