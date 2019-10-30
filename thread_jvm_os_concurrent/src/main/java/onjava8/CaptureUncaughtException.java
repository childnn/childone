package onjava8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/5 10:51
 * 为解决这个问题，需要改变 **Executor** （执行器）生成线程的方式。 **Thread.UncaughtExceptionHandler**
 * 是一个添加给每个 **Thread** 对象，用于进行异常处理的接口。
 * <p>
 * 当该线程即将死于未捕获的异常时，将自动调用 `Thread.UncaughtExceptionHandler.uncaughtException()`
 * 方法。为了调用该方法，我们创建一个新的 **ThreadFactory** 类型来让 **Thread.UncaughtExceptionHandler**
 * 对象附加到每个它所新创建的 **Thread**（线程）对象上。我们赋值该工厂对象给 **Executors** 对象的 方法，
 * 让它的方法来生成新的 **ExecutorService** 对象
 */
public class CaptureUncaughtException {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
        exec.execute(new ExceptionThread2());
        exec.shutdown();
    }
}

class ExceptionThread2 implements Runnable {
    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.println("run() by " + t.getName());
        System.out.println("eh = " + t.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("caught " + e);
    }
}

class HandlerThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        System.out.println(this + " creating new Thread");
        Thread t = new Thread(r);
        System.out.println("created " + t);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println("eh = " + t.getUncaughtExceptionHandler());
        return t;
    }
}
