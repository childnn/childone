package collection;

import java.util.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/26 13:32
 */
public class Synchronization {
    public static void main(String[] args) {
        Collection<String> c =
                Collections.synchronizedCollection(
                        new ArrayList<>());
        List<String> list = Collections
                .synchronizedList(new ArrayList<>());
        Set<String> s = Collections
                .synchronizedSet(new HashSet<>());
        Set<String> ss = Collections
                .synchronizedSortedSet(new TreeSet<>());
        Map<String, String> m = Collections
                .synchronizedMap(new HashMap<>());
        Map<String, String> sm = Collections
                .synchronizedSortedMap(new TreeMap<>());
    }
}
