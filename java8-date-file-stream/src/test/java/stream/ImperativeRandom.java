package stream;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see Randoms 声明式编程 declaritive
 * 在 {@link ImperativeRandom}` 中显式地编写迭代机制称为外部迭代。
 * 而在 {@link Randoms} 中，流式编程采用内部迭代，这是流式编程的核心特性之一。
 * 另一个重要方面，流是懒加载的。这代表着它只在绝对必要时才计算。你可以将流看作“延迟列表”。
 * 由于计算延迟，流使我们能够表示非常大（甚至无限）的序列，而不需要考虑内存问题。
 * ---
 * 流操作的类型有三种：创建流，修改流元素（中间操作， Intermediate Operations），
 * 消费流元素（终端操作， Terminal Operations）。最后一种类型通常意味着收集流元素（通常是到集合中）。
 * @since 2020/3/14 16:39
 */
public class ImperativeRandom {
    public static void main(String[] args) {
        Random rand = new Random(47);
        SortedSet<Integer> rints = new TreeSet<>();
        while (rints.size() < 7) {
            int r = rand.nextInt(20);
            if (r < 5) continue;
            rints.add(r);
        }
        System.out.println(rints);
    }
}
