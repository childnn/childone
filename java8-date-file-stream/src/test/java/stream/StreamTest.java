package stream;

import org.junit.Test;
import pojo.Student;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/22 16:01
 */
public class StreamTest {
    @Test
    public void test() {
        int[] arr = {1, 2, 3, 3};
        IntStream stream = Arrays.stream(arr);
        System.out.println(stream.getClass()); // java.util.stream.IntPipeline$Head
        int[] ints = stream.filter(value -> value > 2) // 过滤.
                .distinct() // 去重.
                .toArray();
        String s = Arrays.toString(ints);
        System.out.println("s = " + s);
    }

    @Test
    public void test1() {
        Stream<Integer> stream = Stream.of("java", "jack", "map", "stream")
                .map(String::length); // 计算 string 长度, 转换 stream. -- 映射.
        System.out.println("stream = " + stream);
        List<Integer> list = stream.collect(Collectors.toList());
        System.out.println("list = " + list);
    }

    @Test
    public void test2() {
        Stream<String[]> stream = Stream.of("1,2,3").map(s -> s.split(","));
        // List<String[]> collect = stream.collect(Collectors.toList());
        /*stream.flatMap(new Function<String[], Stream<?>>() {
            @Override
            public Stream<?> apply(String[] strings) {
                return Arrays.stream(strings);
            }
        });*/
        Stream<String> stream1 = stream.flatMap(Arrays::stream);
        String collect = stream1.collect(Collectors.joining("-", "'", "'"));
        System.out.println(collect);
    }

    @Test
    public void test3() {
        long count = Stream.of(1, 2, 3).count();
        System.out.println("count = " + count);
    }

    @Test
    public void test4() {
        Optional<Integer> reduce = Stream.of(1, 2, 3, 4).reduce(Integer::sum);
        reduce.ifPresent(System.out::println);
    }

    @Test
    public void test5() {
        Integer reduce = Stream.of(1, 2, 3, 4, 5).reduce(12, Integer::sum);
        System.out.println("reduce = " + reduce);
    }

    @Test
    public void test6() {
        @SuppressWarnings("serial")
        List<Student> list = new ArrayList<Student>(){{
            add(new Student().setAge(1).setName("jack"));
            add(new Student().setAge(2).setName("jack"));
            add(new Student().setAge(2).setName("jack"));
            add(new Student().setAge(4).setName("jack"));
            add(new Student().setAge(4).setName("jack"));
            add(new Student().setAge(1).setName("jack"));
        }};
        IntStream intStream = list.stream().mapToInt(Student::getAge);

        /*IntStream intStream = list.stream().mapToInt(new ToIntFunction<Student>() {
            @Override
            public int applyAsInt(Student value) {
                return value.getAge();
            }

        });*/
//        Stream<Integer> boxed = intStream.boxed(); // 装箱.
        /*OptionalInt any = intStream.findAny();
        if (any.isPresent()) {
            int asInt = any.getAsInt();
            System.out.println("asInt = " + asInt);
        }*/
        OptionalInt first = intStream.findFirst();
        first.ifPresent(System.err::println);

    }


    // 分组.
    @Test
    public void test7() {
        @SuppressWarnings("serial")
        List<Student> list = new ArrayList<Student>(){{
            add(new Student().setAge(1).setName("jack"));
            add(new Student().setAge(2).setName("jack"));
            add(new Student().setAge(2).setName("jack"));
            add(new Student().setAge(4).setName("jack"));
            add(new Student().setAge(4).setName("jack"));
            add(new Student().setAge(1).setName("jack"));
        }};
        Map<Integer, List<Student>> collect = list.stream().collect(Collectors.groupingBy(Student::getAge));
        System.out.println("collect = " + collect);
    }



}
