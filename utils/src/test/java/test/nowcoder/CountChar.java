package test.nowcoder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/06/06
 */
public class CountChar {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String src = sc.nextLine();
        Scanner sc1 = new Scanner(System.in);
        String dest = sc1.next();
        System.out.println(count(src, dest));
    }

    public static int count(String src, String dest) {
        String destUpper = dest.toUpperCase();
        // char[] chars = src.toCharArray();

        Set<Integer> set = new HashSet<>();

        // SortedSet<Integer> s = new TreeSet<>();
        // set.forEach();


        String[] split = src.split("");
        return (int) Stream.of(split)
                .map(String::toUpperCase)
                .filter(s -> Objects.equals(s, destUpper))
                .count();

        // Stream<String> ss = Arrays.asList()
        //         .stream()
        //         .map(String::valueOf)
        //         .map(String::toUpperCase);
        // List<String> collect = ss.collect(Collectors.toList());
        //
        // return (int) ss
        //         .filter(s -> Objects.equals(s, destUpper))
        //         .count();
    }

}
