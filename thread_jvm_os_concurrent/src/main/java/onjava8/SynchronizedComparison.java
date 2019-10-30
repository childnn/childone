package onjava8;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/7 19:31
 * 测试同步方法和同步代码块的性能
 */
public class SynchronizedComparison {

    static void test(Guarded g) {
        List<CompletableFuture<Void>> callers =
                Stream.of(
                        new Caller(g),
                        new Caller(g),
                        new Caller(g),
                        new Caller(g))
                        .map(CompletableFuture::runAsync)
                        .collect(Collectors.toList());
        callers.forEach(CompletableFuture::join);
        System.out.println(g);
    }

    public static void main(String[] args) {
        test(new CriticalSection());
        test(new SynchronizedMethod());
    }


}

abstract class Guarded {

    AtomicLong callCount = new AtomicLong();

    public abstract void method();

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                ": " + callCount.get();
    }

}

class SynchronizedMethod extends Guarded {
    public synchronized void method() {
        new Nap(0.01);
        callCount.incrementAndGet();
    }
}

class CriticalSection extends Guarded {
    public void method() {
        new Nap(0.01);
        synchronized (this) {
            callCount.incrementAndGet();
        }
    }
}

class Caller implements Runnable {
    private Guarded g;
    private AtomicLong successfulCalls = new AtomicLong();
    private AtomicBoolean stop = new AtomicBoolean(false);

    Caller(Guarded g) {
        this.g = g;
    }

    @Override
    public void run() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                stop.set(true);
            }
        }, 2500);

        while (!stop.get()) {
            g.method();
            successfulCalls.getAndIncrement();
        }
        System.out.println("-> " + successfulCalls.get());
    }

}