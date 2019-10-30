package lambda;

import org.junit.Test;

import java.util.Optional;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/1 10:24
 * 采用 Supplier 作为方法参数是 延迟执行 deferred execution 或
 * 惰性执行 lazy execution 的一种应用，从而可以仅在需要时才在 Supplier 上
 * 调用 get 方法。
 */
public class LazySupplier {
    @Test
    public void test() {
        Optional<AObj> o = Optional.of(new AObj());
        System.out.println("=====================");
        o.orElse(new AObj()); // 总是创建新对象
        System.out.println("=====================");
        o.orElseGet(AObj::new); // 仅在需要的时候创建对象
    }
}

class AObj {
    public AObj() {
        System.out.println("construct");
    }
}
