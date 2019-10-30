package onjava8;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/10 15:53
 */
public class QuittableTask implements Runnable {
    final int id;
    private AtomicBoolean running = new AtomicBoolean(true);

    public QuittableTask(int id) {
        this.id = id;
    }

    // 虽然多个任务可以在同一个实例上成功调用**quit()**，但是**AtomicBoolean**可以防止多个任务同时实际修改**running**，
    // 从而使**quit()**方法成为线程安全的。
    public void quit() {
        running.set(false);
    }

    @Override
    public void run() {
        while (running.get())         // [1] 只要运行标志为true，此任务的run()方法将继续。
            new Nap(0.1);
        System.out.print(id + " ");  // [2] 显示仅在任务退出时发生
    }
}

class QuittingTasks {
    public static final int COUNT = 150;

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        List<QuittableTask> tasks =
                IntStream.range(1, COUNT)
                        .mapToObj(QuittableTask::new)
                        .peek(es::execute)
                        .collect(Collectors.toList());
        new Nap(1);
        tasks.forEach(QuittableTask::quit);
        es.shutdown();
    }
}

class QuittingCompletable {
    public static void main(String[] args) {
        List<QuittableTask> tasks =
                IntStream.range(1, QuittingTasks.COUNT)
                        .mapToObj(QuittableTask::new)
                        .collect(Collectors.toList());
        List<CompletableFuture<Void>> cfutures =
                tasks.stream()
                        .map(CompletableFuture::runAsync)
                        .collect(Collectors.toList());
        new Nap(1);
        tasks.forEach(QuittableTask::quit);
        cfutures.forEach(CompletableFuture::join);
    }
}