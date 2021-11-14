package stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/4/23 15:42
 */
public class CollectorMap {

    @Test
    public void test1() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 1, 3);
        Map<Integer, Integer> collect = list.stream().collect(Collectors.toMap(
                Integer::intValue, Function.identity()));
    }

    @Test
    public void test2() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "1", "3");
        Map<String, String> collect = list.stream().collect(Collectors.toMap(
                String::toString, Function.identity()));
    }

    @Test
    public void test() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "1", "3", "1");
        Map<String, String> collect = list.stream().collect(Collectors.toMap(
                String::toString, Function.identity(),
                // 这来参数表示 key 冲突时, 两个 value 如何处理
                (i1, i2) -> i1 + "," + i2));
        System.out.println("collect = " + collect);
    }



}
