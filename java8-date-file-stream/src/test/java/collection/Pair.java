package collection;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/24 20:08
 * **Pair** 是一个只读的 *数据传输对象* （Data Transfer Object）或 *信使* （Messenger）。
 */
public class Pair<K, V> {
    public final K key;
    public final V value;

    public Pair(K k, V v) {
        key = k;
        value = v;
    }

    public static <K, V> Pair<K, V> make(K k, V v) {
        return new Pair<>(k, v);
    }

    public K key() {
        return key;
    }

    public V value() {
        return value;
    }
}
