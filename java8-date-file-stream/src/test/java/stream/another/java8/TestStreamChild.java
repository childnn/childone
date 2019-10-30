package stream.another.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.util.stream.Stream#reduce(Object, BiFunction, BinaryOperator)
 * 参数三只有在并行流
 * @since 2019/12/19 11:05
 */
public class TestStreamChild {

    @Test
    public void test() {
        final AtomicInteger accumulateCount = new AtomicInteger(0);
        final AtomicInteger combineCount = new AtomicInteger(0);

        List<Integer> list = /*Arrays.asList(1000, 2000)*/new ArrayList<>(); // 如果集合有数据, 结果会有问题??? don't know why.
        List<Integer> reduce = IntStream.range(0, 100).parallel().boxed().reduce(
                list,
//                new BiFunction<List<Integer>, Integer, List<Integer>>() {
//                    @Override
//                    public List<Integer> apply(List<Integer> integers, Integer integer) {
//                        integers.add(integer);
//                        return integers;
//                    }
//                },
                (lst, i) -> {
                    int a = accumulateCount.incrementAndGet();// ++i;
//                    accumulateCount.getAndIncrement(); // i++;
                    System.err.println(String.format("thread name: %s, lst: %s, i: %s", Thread.currentThread(), lst, i));
//                    lst.add(i); // 直接 add, 这里会
                    // 不改变初始的集合, 返回一个新的集合.
                    List<Integer> newLst = new ArrayList<>(lst);
                    newLst.add(i);

                    return newLst;
                },
                (a, b) -> {
                    int i = combineCount.incrementAndGet();
                    System.out.println(String.format("thread name: %s, a = %s, b = %s", Thread.currentThread().getName(), a, b));
                    a.addAll(b);
                    return a;
                }
        );
        System.out.println("reduce = " + reduce);
    }

}
