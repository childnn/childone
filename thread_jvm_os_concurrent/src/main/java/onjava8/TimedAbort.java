package onjava8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/6 18:11
 */
public class TimedAbort {
    private volatile boolean restart = true;

    public TimedAbort(double t, String msg) {
        CompletableFuture.runAsync(() -> {
            try {
                while (restart) {
                    restart = false;
                    TimeUnit.MILLISECONDS.sleep((int) (1000 * t));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(msg);
            System.exit(0);
        });
    }

    public TimedAbort(double t) {
        this(t, "TimedAbort " + t);
    }

    public void restart() {
        restart = true;
    }

}
