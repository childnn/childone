package stream;

import java.util.Random;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see ImperativeRandom
 * @since 2020/3/14 16:28
 * 首先，我们给 **Random** 对象一个种子（以便程序再次运行时产生相同的输出）。
 * `ints()` 方法产生一个流并且 `ints()` 方法有多种方式的重载 — 两个参数限定了数值产生的边界。
 * 这将生成一个整数流。我们可以使用中间流操作（intermediate stream operation） `distinct()`
 * 来获取它们的非重复值，然后使用 `limit()` 方法获取前 7 个元素。接下来，我们使用 `sorted()`
 * 方法排序。最终使用 `forEach()` 方法遍历输出，它根据传递给它的函数对每个流对象执行操作。
 * 在这里，我们传递了一个可以在控制台显示每个元素的方法引用。`System.out::println` 。
 * ---
 * 声明式编程（Declarative programming）是一种：声明要做什么，而非怎么做的编程风格。
 * 正如我们在函数式编程中所看到的。**注意**，命令式编程的形式更难以理解。
 */
public class Randoms {
    public static void main(String[] args) {
        new Random(47)
                .ints(5, 20)
                .distinct()
                .limit(7)
                .sorted()
                .forEach(System.out::println);
    }
}
