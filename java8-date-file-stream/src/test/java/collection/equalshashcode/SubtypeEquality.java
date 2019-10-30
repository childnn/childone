package collection.equalshashcode;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

enum Size {SMALL, MEDIUM, LARGE}

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/26 17:23
 */
public class SubtypeEquality {
    public static void main(String[] args) {
        Set<Animal> pets = new HashSet<>();
        // 当我们提供了两个有着相同数据的不同的对象类型，然后将他们放置在 **HashSet<Animal>** 中。
        // 只有他们中的一个能存活。这强调了 **equals()** 不是完美的数学理论，而只是机械般的理论。
        // **hashCode()** 和 **equals()** 必须能够允许类型在hash数据结构中正常工作。例子中 **Dog**
        // 和 **Pig** 会被映射到同 **HashSet** 的同一个桶中。这个时候，**HashSet** 回退到 **equals()**
        // 来区分对象，但是 **equals()** 也认为两个对象是相同的。**HashSet**因为已经有一个相同的对象了，所以没有添加 **Pig**。
        // ---
        // 我们依然能够通过使得其他字段对象不同来让例子能够正常工作。在这里每个 **Animal** 已经有了一个独一无二的 **id** ，
        // 所以你能够取消  **equals()** 函数中的 **[1]** 行注释，或者取消 **hashCode()** 函数中的 **[2]** 行注释。
        // 按照规范，你应该同时完成这两个操作，如此能够将所有“不变的”字段包含在两个操作中（“不变”所以 **equals()**
        // 和 **hashCode()** 在哈希数据结构中的排序和取值时，不会生成不同的值。我将“不变的”放在引号中因为你必须计算出是否已经发生变化）。
        pets.add(new Dog("Ralph", Size.MEDIUM));
        pets.add(new Pig("Ralph", Size.MEDIUM));
        pets.forEach(System.out::println);
    }
}

class Animal {
    private static int counter = 0;
    private final int id = counter++;
    private final String name;
    private final Size size;

    Animal(String name, Size size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public boolean equals(Object rval) {
        return rval instanceof Animal &&
                // Objects.equals(id, ((Animal)rval).id) && // [1]
                Objects.equals(name, ((Animal) rval).name) &&
                Objects.equals(size, ((Animal) rval).size);
    }

    // 注**： 在**hashCode()**中，如果你只能够使用一个字段，使用**Objcets.hashCode()**。如果你使用多个字段，那么使用 **Objects.hash()**。
    @Override
    public int hashCode() {
        return Objects.hash(name, size);
        // return Objects.hash(name, size, id); // [2]
    }

    @Override
    public String toString() {
        return String.format("%s[%d]: %s %s %x",
                getClass().getSimpleName(), id,
                name, size, hashCode());
    }
}

class Dog extends Animal {
    Dog(String name, Size size) {
        super(name, size);
    }
}

class Pig extends Animal {
    Pig(String name, Size size) {
        super(name, size);
    }
}