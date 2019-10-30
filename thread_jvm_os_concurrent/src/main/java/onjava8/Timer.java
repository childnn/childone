package onjava8;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/9 20:35
 */
public class Timer {

    private long start = System.nanoTime();

    public static long duration(Runnable test) {
        Timer timer = new Timer();
        test.run();
        return timer.duration();
    }

    public long duration() {
        return NANOSECONDS.toMillis(System.nanoTime() - start);
    }

}
