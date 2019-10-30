package stream;

import org.junit.Test;
import pojo.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/7 10:35
 */
public class StreamCollectorsTest {
    @Test
    public void test1() {
        Set<Integer> collect = Stream.of(1, 2, 3, 4, 5).collect(Collectors.toSet());
        System.out.println("collect = " + collect.getClass());
    }

    @Test
    public void test2() {
        List<String> collect = Stream.of("1", "2", "3", "4", "5").collect(Collectors.toList());

    }

    // 分组.
    @Test
    public void test3() {
        @SuppressWarnings("serial")
        List<Student> list = new ArrayList<Student>(){{
            add(new Student().setAge(1).setName("jack"));
            add(new Student().setAge(2).setName("jack"));
            add(new Student().setAge(2).setName("rose"));
            add(new Student().setAge(4).setName("tom"));
            add(new Student().setAge(4).setName("tom"));
            add(new Student().setAge(1).setName("tom"));
        }};
        // key: 分组字段类型.
        Map<String, List<Student>> collect = list.stream().collect(Collectors.groupingBy(Student::getName));
        System.out.println("collect = " + collect);
    }

    // 分区: 是/否 -- map 的 key: true/false.
    @Test
    public void test4() {
        @SuppressWarnings("serial")
        List<Student> list = new ArrayList<Student>(){{
            add(new Student().setAge(1).setName("jack"));
            add(new Student().setAge(2).setName("jack"));
            add(new Student().setAge(2).setName("rose"));
            add(new Student().setAge(4).setName("tom"));
            add(new Student().setAge(4).setName("tom"));
            add(new Student().setAge(10).setName("tom"));
        }};
        Map<Boolean, List<Student>> collect = list.stream().collect(Collectors.partitioningBy(s -> s.getAge() > 5));
        System.out.println("collect = " + collect);
    }
}
