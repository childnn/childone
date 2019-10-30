package collection.equalshashcode;

import collection.flyweight.Countries;

import java.util.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/27 12:06
 * 由于散列表中的“槽位”（slot）通常称为桶位（bucket），因此我们将表示实际散列表的数组命名为bucket，
 * 为使散列分布均匀，桶的数量通常使用质数。注意，为了能够自动处理冲突，使用了一个LinkedList的数组；
 * 每一个新的元素只是直接添加到list尾的某个特定桶位中。即使Java不允许你创建泛型数组，那你也可以创建指向这种数组的引用。
 * 这里，向上转型为这种数组是很方便的，这样可以防止在后面的代码中进行额外的转型。
 * ---
 * 事实证明，质数实际上并不是散列桶的理想容量。近来，（经过广泛的测试）Java的散列函数都使用2的整数次方。
 * 对现代的处理器来说，除法与求余数是最慢的操作。使用2的整数次方长度的散列表，可用掩码代替除法。
 * ---
 * 你无法控制bucket数组的下标值的产生。这个值依赖于具体的HashMap对象的容量，
 * 而容量的改变与容器的充满程度和负载因子有关。hashCode()生成的结果，
 * 经过处理后成为桶位的下标（在SimpleHashMap中，只是对其取模，模数为bucket数组的大小）。
 * ---
 * 设计hashCode()时最重要的因素就是：无论何时，对同一个对象调用hashCode()都应该生成同样的值。
 * 如果在将一个对象用put()添加进HashMap时产生一个hashCode()值，而用get()取出时却产生了另一个hashCode()值，
 * 那么就无法重新取得该对象了。所以，如果你的hashCode()方法依赖于对象中易变的数据，用户就要当心了，
 * 因为此数据发生变化时，hashCode()就会生成一个不同的散列码，相当于产生了一个不同的键。
 * -----
 * - 容量（Capacity）：表中存储的桶数量。
 * - 初始容量（Initial Capacity）：当表被创建时，桶的初始个数。 HashMap 和 HashSet 有可以让你指定初始容量的构造器。
 * - 个数（Size）：目前存储在表中的键值对的个数。
 * - 负载因子（Load factor）：通常表现为 $\frac{size}{capacity}$。
 * 当负载因子大小为 0 的时候表示为一个空表。当负载因子大小为 0.5 表示为一个半满表（half-full table），
 * 以此类推。轻负载的表几乎没有冲突，因此是插入和查找的最佳选择（但会减慢使用迭代器进行遍历的过程）。
 * HashMap 和 HashSet 有可以让你指定负载因子的构造器。当表内容量达到了负载因子，集合就会自动扩充为
 * 原始容量（桶的数量）的两倍，并且会将原始的对象存储在新的桶集合中（也被称为 rehashing）
 * ----
 * HashMap 中负载因子的大小为 0.75（当表内容量大小不足四分之三的时候，不会发生 rehashing 现象）。
 * 这看起来是一个非常好的同时考虑到时间和空间消耗的平衡策略。更高的负载因子会减少空间的消耗，
 * 但是会增加查询的耗时。重要的是，查询操作是你使用的最频繁的一个操作（包括 `get()` 和 `put()` 方法）。
 */
public class SimpleHashMap<K, V> extends AbstractMap<K, V> {

    // Choose a prime number for the hash table
    // size, to achieve a uniform distribution:
    static final int SIZE = 997;

    // You can't have a physical array of generics,
    // but you can upcast to one:
    @SuppressWarnings("unchecked")
    LinkedList<MapEntry<K, V>>[] buckets = new LinkedList[SIZE];

    public static void main(String[] args) {
        SimpleHashMap<String, String> m = new SimpleHashMap<>();
        m.putAll(Countries.capitals(8));
        m.forEach((k, v) -> System.out.println(k + "=" + v));
        System.out.println(m.get("BENIN"));
        m.entrySet().forEach(System.out::println);
    }

    @Override
    public V put(K key, V value) {
        V oldValue = null;
        // absolute value
        int index = index(key);
        //int index = Math.abs(key.hashCode()) % SIZE;
        if (buckets[index] == null)
            buckets[index] = new LinkedList<>();

        LinkedList<MapEntry<K, V>> bucket = buckets[index];
        MapEntry<K, V> pair = new MapEntry<>(key, value);
        boolean found = false;
        ListIterator<MapEntry<K, V>> it = bucket.listIterator();
        while (it.hasNext()) {
            MapEntry<K, V> iPair = it.next();
            if (iPair.getKey().equals(key)) {
                oldValue = iPair.getValue();
                it.set(pair); // Replace old with new
                found = true;
                break;
            }
        }
        if (!found)
            buckets[index].add(pair);
        return oldValue;
    }

    @Override
    public V get(Object key) {
        int index = index(key);
        //int index = Math.abs(key.hashCode()) % SIZE;
        if (buckets[index] == null) return null;
        for (MapEntry<K, V> iPair : buckets[index])
            if (iPair.getKey().equals(key))
                return iPair.getValue();
        return null;
    }

    int index(Object key) {
        return Math.abs(key.hashCode()) % SIZE;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for (LinkedList<MapEntry<K, V>> bucket : buckets) {
            if (bucket == null) continue;
            set.addAll(bucket);
            //for (MapEntry<K, V> mpair : bucket)
            //    set.add(mpair);
        }
        return set;
    }
}
