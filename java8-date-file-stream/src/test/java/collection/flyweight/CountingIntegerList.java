package collection.flyweight;

import java.util.AbstractList;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see CountMap
 * @since 2020/3/25 11:00
 * 本节介绍如何创建自定义 **Collection** 和 **Map** 实现。
 * 每个 **java.util** 中的集合都有自己的 **Abstract** 类，它提供了该集合的部分实现，
 * 因此只需要实现必要的方法来生成所需的集合。你将看到通过继承 **java.util.Abstract** 类来创建自定义
 * **Map** 和 **Collection** 是多么简单。例如，要创建一个只读的 **Set** ，则可以从 **AbstractSet** 继承
 * 并实现 `iterator()` 和 `size()` 。最后一个示例是生成测试数据的另一种方法。生成的集合通常是只读的，并且所提供的方法最少。
 * <p>
 * 该解决方案还演示了 *享元* （Flyweight）设计模式。当普通解决方案需要太多对象时，或者当生成普通对象占用太多空间时，
 * 可以使用享元。享元设计模式将对象的一部分外部化（externalizes）。相比于把对象的所有内容都包含在对象中，
 * 这样做使得对象的部分或者全部可以在更有效的外部表中查找，或通过一些节省空间的其他计算生成。
 * <p>
 * 下面是一个可以是任何大小的 **List** ，并且（有效地）使用 **Integer** 数据进行预初始化。要从 **AbstractList**
 * 创建只读 **List** ，必须实现 `get()` 和 `size()`：
 * ---
 * 只有当想要限制 **List** 的长度时， **size** 值才是重要的，就像在主方法中那样。即使在这种情况下， `get()` 也会产生任何值。
 * 这个类是享元模式的一个简洁的例子。当需要的时候， `get()` “计算”所需的值，因此没必要存储和初始化实际的底层 **List** 结构。
 */
public class CountingIntegerList extends AbstractList<Integer> {
    private int size;

    public CountingIntegerList() {
        size = 0;
    }

    public CountingIntegerList(int size) {
        this.size = Math.max(size, 0);
        //this.size = size < 0 ? 0 : size;
    }

    public static void main(String[] args) {
        List<Integer> cil = new CountingIntegerList(30);
        System.out.println(cil);
        System.out.println(cil.get(500));
    }

    @Override
    public Integer get(int index) {
        return index;
    }

    @Override
    public int size() {
        return size;
    }
}
