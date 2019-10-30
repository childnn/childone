package collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/24 16:45
 */
public class SetOrder {
    static final List<String> RLIST =
            new ArrayList<>(HTMLColors.LIST);
    static String[] sets = {
            "java.util.HashSet",
            "java.util.TreeSet",
            "java.util.concurrent.ConcurrentSkipListSet",
            "java.util.LinkedHashSet",
            "java.util.concurrent.CopyOnWriteArraySet",
    };

    static {
        Collections.reverse(RLIST);
    }

    public static void main(String[] args) throws Exception {
        for (String type : sets) {
            System.out.format("[-> %s <-]%n",
                    type.substring(type.lastIndexOf('.') + 1));
            @SuppressWarnings("unchecked")
            Set<String> set = (Set<String>) Class.forName(type).newInstance();
            set.addAll(RLIST);
            set.stream().limit(10)
                    .forEach(System.out::println);
        }
    }
}
