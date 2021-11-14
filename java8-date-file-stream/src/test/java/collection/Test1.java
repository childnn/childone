package collection;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/6/16 10:12
 */
public class Test1 {

    @org.junit.Test
    public void singletonList() {
        ArrayList<Integer> lis = new ArrayList<>();
        lis.add(1);
        System.out.println("lis = " + lis);

        // immutable
        List<Integer> sl = Collections.singletonList(1);
        Set<Integer> set = Collections.singleton(1);

        sl.add(2); // java.lang.UnsupportedOperationException

        List<Integer> list = Arrays.asList(1, 2, 3); // immutable
    }

    @Test
    public void join1() {
        String comma = ",";
        List<Integer> list = Arrays.asList(1, 2, 3);
        String join = list.stream().map(String::valueOf).collect(Collectors.joining(comma));
        String joi = String.join(comma, Arrays.asList("1", "2", "3"));

        // 1,2,3
        System.out.println("joi = " + joi);
        System.out.println("join = " + join);

        // 方式二:
        int size = list.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(list.get(i));
            if (i != size - 1) {
                sb.append(comma);
            }
        }
        System.out.println("sb = " + sb);
    }

    @Test
    public void join() {
        String comma = ",";
        List<Double> list = Stream.generate(Math::random).limit(4).collect(Collectors.toList());
        System.out.println(list);
        System.out.println(list.stream().map(String::valueOf).collect(Collectors.joining(comma)));
        System.out.println(String.join(comma, list.stream().map(String::valueOf).toArray(String[]::new)));
    }

}
