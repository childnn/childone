package collection;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static collection.Government.LENGTH;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/24 18:56
 */
public class SuppliersCollectionTest {
    public static void main(String[] args) {
        // Suppliers class from the Generics chapter:
        Set<String> set = Suppliers.create(LinkedHashSet::new, new Government(), LENGTH);
        System.out.println(set);
        List<String> list = Suppliers.create(LinkedList::new, new Government(), LENGTH);
        System.out.println(list);
        list = new ArrayList<>();
        Suppliers.fill(list, new Government(), LENGTH);
        System.out.println(list);

        // Or we can use Streams:
        set = Arrays.stream(Government.foundation)
                .collect(Collectors.toSet());
        System.out.println(set);
        list = Arrays.stream(Government.foundation)
                .collect(Collectors.toList());
        System.out.println(list);
        list = Arrays.stream(Government.foundation)
                .collect(Collectors.toCollection(LinkedList::new));
        System.out.println(list);
        set = Arrays.stream(Government.foundation)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        System.out.println(set);
    }

}

class Government implements Supplier<String> {
    static String[] foundation = (
            "strange women lying in ponds " +
                    "distributing swords is no basis " +
                    "for a system of government").split(" ");
    public static final int LENGTH = foundation.length;

    private int index;

    @Override
    public String get() {
        return foundation[index++];
    }

}