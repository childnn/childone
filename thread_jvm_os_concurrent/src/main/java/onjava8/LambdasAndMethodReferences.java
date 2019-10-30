package onjava8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/9 22:45
 */
public class LambdasAndMethodReferences {
    // 这里，前两个**submit()**调用可以改为调用**execute()**。所有**submit()**调用都返回**Futures**，你可以在后两次调用的情况下提取结果
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.submit(() -> System.out.println("Lambda1"));
        exec.submit(new NotRunnable()::go);
        exec.submit(() -> {
            System.out.println("Lambda2");
            return 1;
        });
        exec.submit(new NotCallable()::get);
        exec.shutdown();
    }
}

class NotRunnable {
    public void go() {
        System.out.println("NotRunnable");
    }
}

class NotCallable {
    public Integer get() {
        System.out.println("NotCallable");
        return 1;
    }
}