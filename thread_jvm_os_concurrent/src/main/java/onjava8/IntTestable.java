package onjava8;

import java.util.function.IntSupplier;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/7 15:51
 */
public abstract class IntTestable implements Runnable, IntSupplier {

    abstract void evenIncrement();

    @Override
    public void run() {
        while (true)
            evenIncrement();
    }

}
