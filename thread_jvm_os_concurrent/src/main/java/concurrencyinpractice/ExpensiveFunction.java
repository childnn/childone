package concurrencyinpractice;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/21 0:05
 */
public class ExpensiveFunction implements Function<String, BigInteger> {
    @Override
    public BigInteger apply(String s) {
        // after deep thought....
        return new BigInteger(s);
    }
}

/**
 * HashMap + synchronized, 低性能
 *
 * @param <A>
 * @param <V>
 */
class Memo1<A, V> implements Function<A, V> {
    private final Map<A, V> cache = new HashMap<>();
    private final Function<A, V> f;

    public Memo1(Function<A, V> f) {
        this.f = f;
    }

    @Override
    public synchronized V apply(A a) {
        V result = cache.get(a);
        if (result == null) {
            result = f.apply(a);
            cache.put(a, result);
        }
        return result;
    }
}

/**
 * ConcurrentHashMap, apply 可能会被多个线程重复执行, 影响缓存作用
 *
 * @param <A>
 * @param <V>
 */
class Memo2<A, V> implements Function<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final Function<A, V> f;

    public Memo2(Function<A, V> f) {
        this.f = f;
    }

    @Override
    public V apply(A a) {
        V result = cache.get(a);
        if (result == null) {
            result = f.apply(a);
            cache.put(a, result);
        }
        return result;
    }
}

/**
 * 首先检查相应的计算是否已经开始(Memo2 检查计算是否完成), 如果不是, 就创建一个
 * FutureTask, 把他注册到 Map 中, 并开始计算; 如果是, 那么它会等待正在进行的计算.
 * 结果可能很快就会得到, 或者正在运算的过程中.
 * 两个线程可能同时计算相同的值, 因为判断是否开始计算的操作时非原子的--check before run
 * 缓存一个 Future 而不是一个值, 带来了 缓存污染 cache pollution 的可能性: 如果一个计算被取消或者
 * 失败, 未来尝试对这个值进行计算都会表现为取消或者失败. 为了避免这个结果, Memo 如果发现
 * 计算被取消, 就会把 Future 从缓存中移除; 如果发现有 RuntimeException, 也会移除 Future,
 * 这样新请求中的计算才可能成功.
 *
 * @param <A>
 * @param <V>
 */
class Memo3<A, V> implements Function<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Function<A, V> f;

    public Memo3(Function<A, V> f) {
        this.f = f;
    }

    @Override
    public V apply(A a) {
        Future<V> future = cache.get(a);
        // 计算是否开始
        if (future == null) {
            Callable<V> eval = () -> f.apply(a);
            FutureTask<V> ft = new FutureTask<>(eval);
            future = ft;
            cache.put(a, ft);
            ft.run(); // f.apply 的调用发生在 run 中 -- callable.run
        }

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw LoadException.loadThrowable(e.getCause());
        }
    }

}

class LoadException {
    static RuntimeException loadThrowable(Throwable cause) {
        if (cause instanceof RuntimeException) {
            return (RuntimeException) cause;
        } else if (cause instanceof Error) {
            throw (Error) cause;
        }
        throw new IllegalStateException("Not unchecked", cause);
    }
}

// 最终版本
class Memo<A, V> implements Function<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Function<A, V> f;

    public Memo(Function<A, V> f) {
        this.f = f;
    }

    @Override
    public V apply(final A a) {
        while (true) {
            Future<V> future = cache.get(a);
            if (future == null) {
                Callable<V> eval = () -> f.apply(a);
                FutureTask<V> ft = new FutureTask<>(eval);
                // 返回被覆盖的值, 或者 null
                future = cache.putIfAbsent(a, ft);
                if (future == null) {
                    future = ft;
                    ft.run();
                }
            }
            try {
                return future.get();
            } catch (InterruptedException e) {
                cache.remove(a, future); // 移除缓存
            } catch (ExecutionException e) {
                throw LoadException.loadThrowable(e.getCause());
            }
        }
    }
}