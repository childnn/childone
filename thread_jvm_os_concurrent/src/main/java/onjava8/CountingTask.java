package onjava8;

import java.util.concurrent.Callable;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see InterferingTask
 * @since 2020/4/9 22:37
 */
public class CountingTask implements Callable<Integer> {
    final int id;

    public CountingTask(int id) {
        this.id = id;
    }

    // **call()完全独立于所有其他CountingTasks生成其结果**，这意味着没有可变的共享状态
    @Override
    public Integer call() {
        Integer val = 0;
        for (int i = 0; i < 100; i++)
            val++;
        System.out.println(id + " " + Thread.currentThread().getName() + " " + val);
        return val;
    }
}
