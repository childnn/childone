package collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/26 13:53
 * Java 集合还具有防止多个进程修改集合内容的机制。如果当前正在迭代集合，然后有其他一些进程介入并插入，
 * 删除或更改该集合中的对象，则会出现此问题。也许在集合中已经遍历过了那个元素，也许还没有遍历到，
 * 也许在调用 `size()` 之后集合的大小会缩小...有许多灾难情景。 Java 集合库使用一种 *fail-fast* 的机制，
 * 该机制可以检测到除了当前进程引起的更改之外，其它任何对集合的更改操作。如果它检测到其他人正在修改集合，
 * 则会立即生成 **ConcurrentModificationException** 异常。这就是“fail-fast”的含义——它不会在以后使用更复杂的算法尝试检测问题（快速失败）。
 */
public class FailFast {
    public static void main(String[] args) {
        Collection<String> c = new ArrayList<>();
        Iterator<String> it = c.iterator();
        // 异常来自于在从集合中获得迭代器之后，又尝试在集合中添加元素。
        // 程序的两个部分可能会修改同一个集合，这种可能性的存在会产生不确定状态，
        // 因此异常会通知你更改代码。在这种情况下，应先将所有元素添加到集合，然后再获取迭代器。
        // **ConcurrentHashMap** ， **CopyOnWriteArrayList** 和 **CopyOnWriteArraySet** 使用了
        // 特定的技术来避免产生 **ConcurrentModificationException** 异常。
        c.add("An object");
        try {
            String s = it.next();
        } catch (ConcurrentModificationException e) {
            System.out.println(e);
        }
    }
}
