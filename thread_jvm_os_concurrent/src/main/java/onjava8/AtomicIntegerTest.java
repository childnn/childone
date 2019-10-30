package onjava8;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/7 17:29
 */
public class AtomicIntegerTest extends IntTestable {

    private AtomicInteger i = new AtomicInteger(0);

    public static void main(String[] args) {
        Atomicity.test(new AtomicIntegerTest());
    }

    @Override
    public int getAsInt() {
        return i.get();
    }

    @Override
    public void evenIncrement() {
        i.addAndGet(2);
    }

}
