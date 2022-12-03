package miaowan;

import java.util.TreeSet;

/**
 * Collection<E>(interface) and Collections(class)
 *  Collection
 *          所有单列集合（区别于双列集合Map）的顶层接口
 *          它抽取出 List和Set接口的共性方法，形成集合类组的一种共同的基本规范
 *          本质上，各个 子单列集合 的父接口(list/set) 可以分别来看,
 *          两大接口的根本区别在于可否【重复】, 次要区别是 [索引], 非必要区别是 [存储有序]
 *          List<E>(interface): 可重复, 有索引
 *                ArrayList: 动态数组(连续内存), 主查询
 *                  查询 O(1) -- java.util.ArrayList#get(int), 增删 O(n)
 *                  源码: ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable
 *                LinkedList：链表(非连续内存), 主增删
 *                  查询 O(n), 增删 O(1)
 *                  源码: LinkedList<E> extends AbstractSequentialList<E> implements List<E>, Deque<E>, Cloneable, java.io.Serializable
 *             关键点: ArrayList 实现 java.util.RandomAccess 接口(标记接口: 空接口)
 *               实现了 RandomAccess 接口的集合, 在执行遍历操作时, 使用 索引 访问, 比 迭代器 访问的方式要快
 *               即, 对 ArrayList 来说,
 *                      for (int i=0, n=list.size(); i < n; i++)
 *                          list.get(i);
 *                  比  for (Iterator i=list.iterator(); i.hasNext(); )
 *                          i.next();
 *                  效率更好.
 *               eg: Collections 工具类的源码:
 *                  public static <T> int binarySearch(List<? extends Comparable<? super T>> list, T key) {
 *                      if (list instanceof RandomAccess || list.size() < BINARYSEARCH_THRESHOLD) //int BINARYSEARCH_THRESHOLD   = 5000;
 *                          return Collections.indexedBinarySearch(list, key);
 *                      else
 *                          return Collections.iteratorBinarySearch(list, key);
 *                  }
 *               该方法使用 "二分查找法", 查找指定集合中的指定元素, 首先判断 目标集合 是否是 RandomAccess 的子类, 如果是(如 ArrayList),
 *               则使用 indexedBinarySearch 方法,  即索引遍历, 否则使用 iteratorBinarySearch, 迭代器遍历.
 *
 *          Set<E>(interface): 元素不可重复，无索引
 *                  HashSet：存取无序
 *                  LinkedHashSet：存取有序
 *                  TreeSet：自然顺序排序
 *
 *    注: [存储有序] 并不是区分 List 与 Set 的关键点,
 *      "所有 List 类型的集合都是 存储有序的", 但是 "并非所有 Set 类型的集合都是存储无序的"
 *      常见特例: LinkedHashSet 就是存储有序的 Set 集合 (这里影响存储顺序的实际上是 "link"(链表) 关键字)
 *
 * Collections
 *          是一个用来操作集合的工具类，只含有静态方法，无法创建对象（构造方法被私有，且没有创建对象的方法(没有类似 newInstance() 的方法),
 *          包含牌 排序（sort），查找（binarySearch​），拷贝（copy），替换（replaceAll），打乱（shuffle）,
 *          可以认为，Collections类是用来为Collection接口及其子类集合服务的.
 *
 *------------------------------------------------------------------------------------------------------------------------
 *
 * 双列集合: Map, 双列集合的根节点
 *  HashMap 与 Hashtable
 *  HashMap:  HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V>, Cloneable, Serializable
 *        非同步, 效率高, 线程不安全
 *  Hashtable: Hashtable<K,V> extends Dictionary<K,V> implements Map<K,V>, Cloneable, java.io.Serializable
 *        同步(多数方法都是同步的: synchronized 修饰), 效率低, 线程安全
 *     二者 继承(extends) 的类不同,
 *     HashMap 获取 键/值 集合的方法:
 *             key:  Set<K> keySet()  (唯一)
 *             value:  Collection<V> values()
 *     Hashtable 获取 键/值 集合的方法:
 *             key: Enumeration<K> keys() 枚举: 实现自 Dictionary (抽象类, 该类已经不推荐使用)
 *                  Set<K> keySet(): 实现自 Map 接口
 *             value: Collection<V> values()
 *   另: Hashtable 的命名没有遵循 驼峰命名 原则...
 *   如果需要使用 线程安全的 map 集合, 一般也不会使用 Hashtable, 而是使用 Collections 工具类的方法
 *     static <K,V> Map<K,V> synchronizedMap(Map<K,V> m)
 *   或者是 jdk 1.5+ 的 JUC 包 下的 ConcurrentHashMap
 *   关于 JUC 包: java.util.concurrent 包, 高并发专用包
 *   有许多线程安全的 容器:
 *      java.util.concurrent.ConcurrentHashMap
 *      java.util.concurrent.CopyOnWriteArrayList
 *      java.util.concurrent.CopyOnWriteArraySet
 *
 * -----------------------------------------------------------------------------------------------------------
 *
 * 关于 HashMap 的 key 的去重基本原理: (与 hashCode() 与 equals() 方法 相关)
 *  根据 key 的 hash 值确定 Node 数组的索引.
 *    基本结论:
 *      hashCode() 方法返回 true 的两个对象, equals() 方法【不一定】返回 true
 *      equals() 方法返回 true 的两个对象， hashCode() 方法【一定】 返回 true
 *      (如果两个对象 相同(equals 为 true), 则 这两个对象的 hashCode 必须相同)
 *     经典例子: 汉字字符串 "通话" 和 "重地", hashCode 相同, 而 equals 为 false
 *       (这也就是所谓的 hash 冲突)
 *     HashMap 的 put() 方法去重的 关键判断:
 *       if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
 *    即: 先判断 当前 put 的 key 是否与原 map 中的 key 同 hash 值,
 *      如果 hash 值 相同, 再判断 equals, 防止 hash 冲突的出现
 *      如果 hash 值 不同, 就不用判断 equals 了.
 *  注: 以上的结论, 必须基于按规范重写的 hashCode() 和 equals() 方法.
 *   如果需要重写 hashCode() 方法, 则必须同时重写 equals() 方法
 *---------------------------------------------------------------
 * java.util.ArrayList#remove(java.lang.Object)
 * remove 方法会依据 equals 判断是否为同一个元素, 进而执行删除.
 *
 */
public class Collection$ {
    public static void main(String[] args) {
        TreeSet<Integer> ts = new TreeSet<>();
        ts.add(132);
        ts.add(132);
        ts.add(1);
        ts.add(12);
        ts.add(105);
        System.out.println("ts = " + ts); //ts = [1, 12, 105, 132]
        System.out.println("通话".hashCode() == "重地".hashCode()); //true
    }
}
