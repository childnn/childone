package org.anonymous.concurrencyinpractice;

import java.math.BigInteger;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/4 17:16
 * @see org.anonymous.concurrencyinpractice.UnsafeCachingFactorizer
 * 细粒度同步的基本示例: 不直接同步 service 方法, 而是缩小 synchronized 块的范围来维护线程安全, 并提高并发性.
 * 在将锁的粒度细化时, 应谨慎控制 synchronized 块不要过小: 不可将一个原子操作分解到多个 synchronized 块中.
 * 不过应尽量从 synchroinzed 块中分离耗时且不影响共享状态的操作. 这样即使在耗时操作的执行过程中, 也不会阻止其他线程访问共享状态.
 * 与 {@link org.anonymous.concurrencyinpractice.UnsafeCachingFactorizer} 不同的是,
 * {@link org.anonymous.concurrencyinpractice.CachedFactorizer} 没有使用 {@link java.util.concurrent.atomic.AtomicLong}
 * 类型的命中计数, 而是重新使用 {@code long} 类型的域. 在这里使用 {@code AutomicLong} 当然是安全的, 但是却得不到
 * 比在 {@link CountingFactory} 更多的好处. 原子变量可以保证单一变量的操作是原子的, 然而我们已经使用 synchronized 块构造了原子操作.
 * 使用两种不同的同步机制会引起混淆, 而且性能域安全也不能从中得到额外的好处.
 * {@link org.anonymous.concurrencyinpractice.CachedFactorizer} 提供了简单性(同步整个方法)与并发性(同步尽可能短的代码路径)
 * 之间的平衡. 请求与释放锁的操作需要开销, 所以将 synchronized 块分解得过于琐碎(比如将 ++hit 分解到它自身得 synchronized 块中)
 * 是不合理的, 即使这样做是为了获得更好的原子性. 当访问状态变量或者执行复合操作期间, {@link org.anonymous.concurrencyinpractice.CachedFactorizer}
 * 会占有锁, 但是执行潜在耗时的因数分解之前, 它会释放锁. 这样既保护了线程安全性, 也不会过多地影响并发性, 每个 synchronized 块的
 * 代码路径已经"足够短"了.
 * 决定 synchroinzed 快的大小需要权衡各种设计要求, 包括安全性(这是绝不能妥协的), 简单性和性能.
 * 有时简单性与性能会彼此冲突, 然而正如 {@link org.anonymous.concurrencyinpractice.CachedFactorizer} 示范的那样,
 * 通常能够从中找到一个合理的平衡.
 * 通常简单性与性能之间是相互牵制的. 实现一个同步策略时, 不要过早的为了性能而牺牲简单性(这是对安全性潜在的威胁).
 * 当使用锁的时候, 你应该清除块中的代码的功能, 以及它的执行过程是否会很耗时. 无论是作运算密集型的操作, 还是在执行一个可能存在的潜在阻塞的操作,
 * 如果线程长时间的占有锁, 就会引起活跃度与性能风险的问题. (关于活跃度危险参见: JavaConcurrencyInPractice.txt)
 * 有些耗时的计算或操作, 比如网络或控制台 I/O, 难以快速完成. 执行这些操作期间不要占有锁.
 *
 */
public class CachedFactorizer {

    private BigInteger lastNumber;
    private BigInteger[] lastFactors;
    private long hits;
    private long cacheHits;

    // 方法同步
    public synchronized long getHits() {
        return hits;
    }

    public synchronized double getCacheHitRatio() {
        return (double) cacheHits / (double) hits;
    }

    /**
     * 另, 引入命中计数 hits 以及一个 cacheHits 计数器, 并在第一个
     * synchronized 块内更新它们.
     * 由于这两个计数器构成了共享的, 可变的状态, 我们必须在访问它们的每处都使用同步.
     * synchronized 块之外的代码独享地操作本地(基于栈地)变量, 这些变量不被跨线程共享, 因此无需同步.
     */
    public void service() {
        BigInteger i = extractFromRequest();
        BigInteger[] factors = null;
        /*
        * 第一个 synchronized 块保护检查在运行的操作,
        * 以检查是否可以返回缓存的结果
        * */
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();
            }
        }
        if (factors == null) {
            factors = factor(i);
            /*
            * 第二个保证缓存 number 和 factors 的同步更新.
            * */
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }
        encodeIntoResponse(factors);
    }

    private void encodeIntoResponse(BigInteger[] factors) {

    }

    private BigInteger extractFromRequest() {
        return null;
    }

    private BigInteger[] factor(BigInteger i) {
        return new BigInteger[0];
    }

}
