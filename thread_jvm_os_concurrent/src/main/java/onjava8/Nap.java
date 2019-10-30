package onjava8;

import java.util.concurrent.TimeUnit;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/6 18:21
 */
public class Nap {
    public Nap(double t) { // Seconds
        try {
            TimeUnit.MILLISECONDS.sleep((int) (1000 * t));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Nap(double t, String msg) {
        this(t);
        System.out.println(msg);
    }
}
