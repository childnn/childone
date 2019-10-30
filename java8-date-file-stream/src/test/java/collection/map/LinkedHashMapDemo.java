package collection.map;

import collection.flyweight.CountMap;

import java.util.LinkedHashMap;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/26 12:19
 */
public class LinkedHashMapDemo {
    public static void main(String[] args) {
        LinkedHashMap<Integer, String> linkedMap =
                new LinkedHashMap<>(new CountMap(9));
        System.out.println(linkedMap);
        // Least-recently-used order:
        linkedMap =
                new LinkedHashMap<>(16, 0.75f, true);
        linkedMap.putAll(new CountMap(9));
        System.out.println(linkedMap);
        for (int i = 0; i < 6; i++)
            linkedMap.get(i);
        System.out.println(linkedMap);
        linkedMap.get(0);
        System.out.println(linkedMap);
    }
}
