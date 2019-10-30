package stream.function;

import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/15 12:07
 */
public class MapTo {

    public static void main(String[] args) {
        Stream.of("5", "7", "9")
                .mapToInt(Integer::parseInt)
                .forEach(n -> System.out.format("%d ", n));
        System.out.println();
        Stream.of("17", "19", "23")
                .mapToLong(Long::parseLong)
                .forEach(n -> System.out.format("%d ", n));
        System.out.println();
        Stream.of("17", "1.9", ".23")
                .mapToDouble(Double::parseDouble)
                .forEach(n -> System.out.format("%f ", n));
    }

}
