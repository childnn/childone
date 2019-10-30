package stream;

import org.junit.Test;

import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/31 10:39
 * 流是惰性的, 在达到终止条件前不会处理元素, 达到终止条件后逐个处理每个元素.
 */
public class LazyInit {

    boolean divByThree(int n) {
        System.out.printf("Inside divByThree with arg %d%n", n);
        return n % 3 == 0;
    }

    int multByTwo(int n) {
        System.out.printf("Inside multByTwo with arg %d%n", n);
        return n * 2;
    }

    @Test
    public void test() {
        IntStream.range(100, 200)
                .map(this::multByTwo)
                .filter(this::divByThree)
        //.findFirst()
        ;
    }
}
