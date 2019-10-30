package stream.another.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class TestStreamAPI1 {

    List<Employee> emps = Arrays.asList(new Employee(102, "李四", 59, 6666.66),
            new Employee(101, "张三", 18, 9999.99), new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77), new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 18, 7777.77), new Employee(105, "田七", 28, 5555.55));

    public static Stream<Character> filterCharacter(String str) {
        List<Character> list = new ArrayList<>();

        for (char ch : str.toCharArray()) {
            list.add(ch);
        }
//        char[] chars = str.toCharArray();

        return list.stream();
    }

    /**
     * 映射
     * map——接收 Lambda ， 将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
     * flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     */
    @Test
    public void test1() {
        Stream<String> str = emps.stream().map(Employee::getName);

        List<String> strList = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        strList.stream().map(String::toUpperCase).forEach(System.out::println);

        System.out.println("---------------------------------------------");

        // 返回值是 嵌套 stream.
        Stream<Stream<Character>> stream = strList.stream().map(TestStreamAPI1::filterCharacter);
        stream.forEach(s -> s.forEach(System.out::println));

        System.out.println("---------------------------------------------");

        // 平滑过渡.
        Stream<Character> stream1 = strList.stream().flatMap(TestStreamAPI1::filterCharacter);
        stream1.forEach(System.out::println);
    }

    /**
     * sorted()——自然排序
     * sorted(Comparator com)——定制排序
     */
    @Test
    public void test2() {
        emps.stream().map(Employee::getName).sorted().forEach(System.out::println);

        System.out.println("------------------------------------");

        emps.stream().sorted((x, y) -> {
            if (x.getAge() != y.getAge()) {
                return Integer.compare(x.getAge(), y.getAge());
            }
            return x.getName().compareTo(y.getName());
        }).forEach(System.out::println);

        System.out.println("------------------------------------");

        // todo
        // 先按 年龄升序, 年龄相同则按薪水降序.
        emps.stream().sorted(Comparator.comparing(Employee::getAge).thenComparing(Comparator.comparingDouble(Employee::getSalary).reversed()))
                .forEach(System.out::println);
    }

}
