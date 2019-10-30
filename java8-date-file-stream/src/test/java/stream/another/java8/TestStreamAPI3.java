package stream.another.java8;

import org.junit.Test;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static stream.another.java8.Employee.Status;

public class TestStreamAPI3 {

    List<Employee> emps = Arrays.asList(new Employee(102, "李四", 79, 6666.66, Status.BUSY),
            new Employee(101, "张三", 18, 9999.99, Status.FREE),
            new Employee(103, "王五", 28, 3333.33, Status.VOCATION),
            new Employee(104, "赵六", 8, 7777.77, Status.BUSY),
            new Employee(104, "赵六", 8, 7777.77, Status.FREE),
            new Employee(104, "赵六", 8, 7777.77, Status.FREE),
            new Employee(105, "田七", 38, 5555.55, Status.BUSY));

    List<Employee> emps0 = Arrays.asList(new Employee(3, "李四", 79, 6666.66, Status.BUSY),
            new Employee(1, "张三", 18, 9999.99, Status.FREE),
            new Employee(2, "王五", 28, 3333.33, Status.VOCATION),
            new Employee(4, "赵六", 8, 7777.77, Status.BUSY),
            new Employee(5, "赵六", 8, 7777.77, Status.FREE),
            new Employee(6, "赵六", 8, 7777.77, Status.FREE),
            new Employee(7, "田七", 38, 5555.55, Status.BUSY));

    /**
     * @see BiFunction <T, U, R>
     * 数据源: T, U
     * 结果: R
     * @see Stream#reduce(java.lang.Object, java.util.function.BiFunction, java.util.function.BinaryOperator)
     * 把参数一作为 参数二的第一个入参.
     * 参数三: 只有在并行的情况下会使用.
     * @see TestStreamChild#test()
     */
    @Test
    public void test() {


        emps.stream().reduce(emps0, new BiFunction<List<Employee>, Employee, List<Employee>>() {
            // 第一次的参数一(emps0): java.util.Arrays$ArrayList
            // 参数二: 源 stream 中的各个元素.
            @Override
            public List<Employee> apply(List<Employee> employees, Employee employee) {
                System.out.println(employees.getClass()); // class java.util.Arrays$ArrayList
                System.out.println("employees = " + employees);
                System.out.println("employee = " + employee);

//                ArrayList<Employee> employees1 = (ArrayList<Employee>) employees;
                ArrayList<Employee> list = new ArrayList<>(employees);
                list.add(employee);

                return list;
            }
        }, BinaryOperator.minBy(new Comparator<List<Employee>>() {
            @Override
            public int compare(List<Employee> o1, List<Employee> o2) {
                System.out.println("o1 = " + o1);
                System.out.println("o2 = " + o2);
                return 0;
            }
        }) /*new BinaryOperator<List<Employee>>() {
            @Override
            public List<Employee> apply(List<Employee> employees, List<Employee> employees2) {
                System.out.println("employees = " + employees);
                System.out.println("employees2 = " + employees2);
                return null;
            }
        }*/);
    }

    /**
     * 归约
     * reduce(T identity, BinaryOperator) / reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
     */
    @Test
    public void test1() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum = list.stream().reduce(0, Integer::sum); // (x, y) -> x + y
        System.out.println(sum);

        Optional<Integer> reduce = list.stream().reduce(BinaryOperator.maxBy(Comparator.naturalOrder())); // 最值.
        reduce.ifPresent(System.out::println);

        Optional<Integer> max = list.stream().max(Integer::compareTo); // 最值.
        max.ifPresent(System.out::println);

        Optional<Double> optional = emps.stream().map(Employee::getSalary).reduce(Double::sum);
//        System.out.println(optional.get());
        optional.ifPresent(System.out::println);

        Integer reduce1 = list.stream().reduce(22, Integer::sum, Integer::sum);
        System.out.println("reduce1 = " + reduce1);

        System.out.println("===================================================");

        int sum1 = emps.stream().mapToInt(Employee::getAge).sum();
        System.out.println("sum1 = " + sum1);

        /*
             U result = identity;
             for (T element : this stream)
                result = accumulator.apply(result, element);
             return result;
        * */
        // 参数一: 初始化值; 参数二: 执行的转换操作;
        Integer reduce3 = emps.stream().reduce(1, (integer, employee) -> integer + employee.getAge(), (x, y) -> x - y);
        System.out.println("reduce3 = " + reduce3);

        Employee child = emps.stream().reduce(new Employee(119, "child", 233, 0, Status.BUSY), new BiFunction<Employee, Employee, Employee>() {
            @Override
            public Employee apply(Employee e1, Employee e2) {
                System.out.println("e1 = " + e1);
                System.out.println("e2 = " + e2);
                return e2; // 前一次操作的返回值作为第二次操作的第一个参数.
            }
        }, /*BinaryOperator.maxBy(Comparator.comparingInt(Employee::getAge))*/new BinaryOperator<Employee>() {
            @Override
            public Employee apply(Employee e1, Employee e2) {
                return null;
            }
        });
        System.out.println("child = " + child);

        Integer reduce2 = emps.stream().reduce(0, new BiFunction<Integer, Employee, Integer>() {
            @Override
            public Integer apply(Integer integer, Employee employee) {
                System.out.println("integer = " + integer);
                return integer + employee.getAge();
            }
        }, BinaryOperator.maxBy(Comparator.comparingInt(Integer::intValue))/*new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                System.out.println("integer = " + integer);
                System.out.println("integer2 = " + integer2);
                return integer + integer2;
            }
        }*/);
        System.out.println("reduce2 = " + reduce2);
    }

    //需求：搜索名字中 “六” 出现的次数
    @Test
    public void test2() {
        Optional<Integer> reduce = emps.stream().map(Employee::getName)
                .flatMap(TestStreamAPI1::filterCharacter).map(ch -> ch.equals('六') ? 1 : 0)
                .reduce(Integer::sum);
//        System.out.println(reduce.get());
        reduce.ifPresent(System.out::println);
    }

    // collect —— 将流转换为其他形式。接收一个 Collector 接口的实现，用于给Stream中元素做汇总的方法
    @Test
    public void test3() {
        List<String> list = emps.stream().map(Employee::getName).collect(Collectors.toList());
        list.forEach(System.out::println);

        System.out.println("----------------------------------");

        Set<String> set = emps.stream().map(Employee::getName).collect(Collectors.toSet());
        set.forEach(System.out::println);

        System.out.println("----------------------------------");

        HashSet<String> hashSet = emps.stream().map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        hashSet.forEach(System.out::println);
    }

    // 统计数据: max, min, avg, count, sum.
    @Test
    public void test4() {

        // 最高薪水. -- 具体的值.
        Optional<Double> max = emps.stream().map(Employee::getSalary)
                .collect(Collectors.maxBy(Double::compare)); // .max(Double::compare);
//        System.out.println(max.get());
        max.ifPresent(System.out::println);

        // 最小薪水的员工. -- 对象.
        Optional<Employee> minExp = emps.stream()
                .collect(Collectors.minBy(Comparator.comparingDouble(Employee::getSalary))); // .min(Comparator.comparingDouble(Employee::getSalary));
//        System.out.println(minExp.get());
        minExp.ifPresent(System.out::println);

        // 薪水总和.
        Double sum = emps.stream().collect(Collectors.summingDouble(Employee::getSalary)); // .mapToDouble(Employee::getSalary).sum();
        System.out.println("sum: " + sum);

        // 薪水均值.
        Double avgSalary = emps.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println("avg: " + avgSalary);

        Long count = emps.stream().collect(Collectors.counting()); // .count();
        System.out.println("count: " + count);

        DoubleSummaryStatistics summaryStatistics = emps.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println("max: " + summaryStatistics.getMax() + " -- avg: " + summaryStatistics.getAverage()
                + " -- count: " + summaryStatistics.getCount() + " -- sum: " + summaryStatistics.getSum() + " -- min: " + summaryStatistics.getMin());
    }

    //分组
    @Test
    public void test5() {
        Map<Status, List<Employee>> map = emps.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(map);
    }

    //多级分组: 先按状态分组, 在每个同状态内部再按年龄分组.
    @Test
    public void test6() {
        Map<Status, Map<String, List<Employee>>> map = emps.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(e -> {
                    if (e.getAge() >= 60) {
                        return "老年";
                    } else if (e.getAge() >= 35) {
                        return "中年";
                    } else {
                        return "成年";
                    }
                })));
//        System.out.println(map);
//        map.forEach((k, v) -> System.out.println(k + " " + v));
        printMap(map);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    void printMap(Map map) {
        map.forEach((k, v) -> System.out.println(k + " " + v));
    }

    //分区: 按指定条件分区 -- true/false.
    @Test
    public void test7() {
        Map<Boolean, List<Employee>> map = emps.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() >= 5000));
//        System.out.println(map);
//        map.forEach((k, v) -> System.out.println(k + " " + v));
        printMap(map);
    }

    // 把名称按指定格式输出.
    @Test
    public void test8() {
        String s = emps.stream().map(Employee::getName).collect(Collectors.joining(", ", "[", "]"));
        System.out.println(s);
    }

    @Test
    public void test9() {
        Optional<Double> sum = emps.stream().map(Employee::getSalary)
                .collect(Collectors.reducing(Double::sum)); // .reduce(Double::sum);
//        System.out.println(sum.get());
        sum.ifPresent(System.out::println);
    }

}
