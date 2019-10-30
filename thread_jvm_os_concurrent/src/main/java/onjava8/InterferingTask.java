package onjava8;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see CountingTask
 * @since 2020/4/9 22:32
 */
public class InterferingTask implements Runnable {
    private static Integer val = 0;
    final int id;

    public InterferingTask(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++)
            val++;
        System.out.println(id + " " + Thread.currentThread().getName() + " " + val);
    }
}
