package org.anonymous.concurrencyinpractice;

import org.anonymous.concurrencyinpractice.annotation.NotThreadSafe;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/3 18:44
 * 尽管原子引用本身线程安全, 但当前类中存在竞争条件, 导致它会产生错误答案.
 * 线程安全性的定义要求无论是多线程中的时序或交替操作, 都要保证不破坏那些不变约束.
 * 当前类的不变约束是: 缓存在 lastFactors 中的各个因子的乘积应该等于缓存在 lastNumber 中的数值.
 * 只有遵守这个不变约束, 当前类才是正确的. 当一个不变约束涉及多个变量时, 变量间不是彼此独立的: 某个变量的值
 * 会制约其他几个变量的值. 因此, 更新一个变量的时候, 要在同一个原子操作中更新其他几个.
 * 在一些特殊的时序中, 当前类可能破坏这一不变约束. 即使是原子引用, 并且每个 set 调用都是原子的, 我们无法保证会同时更新
 * lastNumber 和 lastFactors; 当某个线程只修改了一个变量而另一个还没开始修改时, 其他线程将看到该类违反了不变约束:
 * 当线程 A 尝试获取两个值的时间里, 线程 B 可能已经修改了它们, 线程 A 过后会观察到该类违反了不变约束.
 * @see org.anonymous.concurrencyinpractice.CachedFactorizer, 细粒度的 synchronized 块, 而不是直接同步 service 方法.
 */
@NotThreadSafe
public class UnsafeCachingFactorizer /* implements Servlet */ {
    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

    // 为了保证线程安全, 当然可以加 synchronized, 但此时性能变差, 不建议这么做.
    // 加了 synchronized 关键字, 则表示线程访问互斥, 每次只有一个线程可以方法此方法, 直到
    // 该方法执行完毕, 线程释放锁. 此为 弱并发-poor concurrency
    // 这显然违背了 Servlet 涉及初衷, 同时处理多个请求.
    public void service(/* req, resp */) {
        BigInteger i = extractFromRequest(/* req*/);
        if (i.equals(lastNumber.get())) {
            encodeIntoResponse(/* resp, */lastFactors.get());
        } else {
            BigInteger[] factors = factor(i);
            lastNumber.set(i);
            lastFactors.set(factors);
            encodeIntoResponse(/* resp, */ factors);
        }
    }

    private BigInteger[] factor(BigInteger i) {
        return new BigInteger[0];
    }

    private void encodeIntoResponse(BigInteger[] bigIntegers) {
        // ..
    }

    private BigInteger extractFromRequest() {
        // ...
        return null;
    }

}
