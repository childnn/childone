package stream;

import static stream.RandInts.rands;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/15 13:15
 * 在第一个流中，未使用 `parallel()` ，所以 `rands()` 按照元素迭代出现的顺序显示结果；
 * 在第二个流中，引入`parallel()` ，即便流很小，输出的结果顺序也和前面不一样。
 * 这是由于多处理器并行操作的原因。多次运行测试，结果均不同。多处理器并行操作带来的非确定性因素造成了这样的结果。
 * 在最后一个流中，同时使用了 `parallel()` 和 `forEachOrdered()` 来强制保持原始流顺序。
 * 因此，对非并行流使用 `forEachOrdered()` 是没有任何影响的。
 */
public class ForEach {
    static final int SZ = 14;

    public static void main(String[] args) {
        rands().limit(SZ)
                .forEach(n -> System.out.format("%d ", n));
        System.out.println();
        rands().limit(SZ)
                .parallel()
                .forEach(n -> System.out.format("%d ", n));
        System.out.println();
        rands().limit(SZ)
                .parallel()
                .forEachOrdered(n -> System.out.format("%d ", n));
    }
}
