package collection.equalshashcode;

import collection.flyweight.Countries;

import java.util.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/26 20:36
 * 存储一组元素最快的数据结构是数组，所以使用它来表示键的信息（请小心留意，我是说键的信息，而不是键本身）。
 * 但是因为数组不能调整容量，因此就有一个问题：我们希望在Map中保存数量不确定的值，但是如果键的数量被数组的容量限制了，该怎么办呢？
 * <p>
 * 答案就是：数组并不保存键本身。而是通过键对象生成一个数字，将其作为数组的下标。这个数字就是散列码，由定义在Object中的、
 * 且可能由你的类覆盖的hashCode()方法（在计算机科学的术语中称为散列函数）生成。
 * <p>
 * 于是查询一个值的过程首先就是计算散列码，然后使用散列码查询数组。如果能够保证没有冲突（如果值的数量是固定的，那么就有可能），
 * 那可就有了一个完美的散列函数，但是这种情况只是特例。。通常，冲突由外部链接处理：数组并不直接保存值，而是保存值的 list。
 * 然后对 list中的值使用equals()方法进行线性的查询。这部分的查询自然会比较慢，但是，如果散列函数好的话，数组的每个位置就只有较少的值。
 * 因此，不是查询整个list，而是快速地跳到数组的某个位置，只对很少的元素进行比较。这便是HashMap会如此快的原因。
 */
public class SlowMap<K, V> extends AbstractMap<K, V> {
    private List<K> keys = new ArrayList<>();
    private List<V> values = new ArrayList<>();

    public static void main(String[] args) {
        SlowMap<String, String> m = new SlowMap<>();
        m.putAll(Countries.capitals(8));
        m.forEach((k, v) ->
                System.out.println(k + "=" + v));
        System.out.println(m.get("BENIN"));
        m.entrySet().forEach(System.out::println);
    }

    @Override
    public V put(K key, V value) {
        V oldValue = get(key); // The old value or null
        if (!keys.contains(key)) {
            keys.add(key);
            values.add(value);
        } else
            values.set(keys.indexOf(key), value);
        return oldValue;
    }

    @Override
    public V get(Object key) { // key: type Object, not K
        if (!keys.contains(key))
            return null;
        return values.get(keys.indexOf(key));
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        Iterator<K> ki = keys.iterator();
        Iterator<V> vi = values.iterator();
        while (ki.hasNext())
            set.add(new MapEntry<>(ki.next(), vi.next()));
        return set;
    }
}
