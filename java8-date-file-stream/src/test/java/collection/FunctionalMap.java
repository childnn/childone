package collection;

import java.util.Map;

import static collection.HTMLColors.MAP;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/24 17:15
 */
public class FunctionalMap {
    public static void main(String[] args) {
        //MAP.values().stream()
        MAP.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(v -> v.startsWith("Dark"))
                .map(v -> v.replaceFirst("Dark", "Hot"))
                .forEach(System.out::println);
    }
}
