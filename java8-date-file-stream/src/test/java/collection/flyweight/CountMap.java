package collection.flyweight;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see CountingIntegerList
 * 可以使用享元设计模式来实现具有任何大小数据集的其他“初始化”自定义集合。下面是一个 **Map** ，它为每一个 **Integer** 键产生唯一的值：
 * 要创建一个只读的 **Map** ，则从 **AbstractMap** 继承并实现 `entrySet()` 。
 * 私有的 `value()` 方法计算任何键的值，并在 `get()` 和 `Entry.getValue()` 中使用。可以忽略 **CountMap** 的大小。
 * @since 2020/3/25 16:25
 */
public class CountMap extends AbstractMap<Integer, String> {
    private static final char[] CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final int CHAR_LENGTH = CHARS.length;
    private int size;

    public CountMap(int size) {
        this.size = Math.max(size, 0);
    }

    private static String value(int key) {
        return CHARS[key % CHAR_LENGTH] +
                Integer.toString(key / CHAR_LENGTH);
    }

    public static void main(String[] args) {
        final int size = 6;
        CountMap cm = new CountMap(60);
        System.out.println(cm);
        System.out.println(cm.get(500));
        cm.values().stream()
                .limit(size)
                .forEach(System.out::println);
        System.out.println();
        new Random(47).ints(size, 0, 1000)
                .mapToObj(cm::get)
                .forEach(System.out::println);
    }

    @Override
    public String get(Object key) {
        return value((Integer) key);
    }

    // 这里是使用了 **LinkedHashSet** 而不是创建自定义 **Set** 类，因此并未完全实现享元。只有在调用 `entrySet()` 时才会生成此对象。
    @Override
    public Set<Map.Entry<Integer, String>> entrySet() {
        // LinkedHashSet retains initialization order:
        return IntStream.range(0, size)
                .mapToObj(Entry::new)
                .collect(Collectors
                        .toCollection(LinkedHashSet::new));
    }

    private static class Entry implements Map.Entry<Integer, String> {
        int index;

        Entry(int index) {
            this.index = index;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Entry &&
                    Objects.equals(index, ((Entry) o).index);
        }

        @Override
        public Integer getKey() {
            return index;
        }

        @Override
        public String getValue() {
            return value(index);
        }

        @Override
        public String setValue(String value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(index);
        }
    }
}
