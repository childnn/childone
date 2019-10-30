package onjava8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/5 10:47
 * 这个程序什么也不输出（然而，如果你用 **execute** 方法替换 `submit()` 方法，你就将会看到异常抛出。
 * 这说明在线程中抛出异常是很棘手的，需要特别注意的事情。
 * <p>
 * 你无法捕获到从线程逃逸的异常。一旦异常越过了任务的 `run()` 方法，它就会传递至控制台，除非你采取特殊步骤来捕获此类错误异常。
 */
public class SwallowedException {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.submit(() -> {
            throw new RuntimeException();
        });
        exec.shutdown();
    }
}
