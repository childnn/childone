package onjava8;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/6 18:08
 */
public abstract class IntGenerator {
    private AtomicBoolean canceled = new AtomicBoolean();

    public abstract int next();

    public void cancel() {
        canceled.set(true);
    }

    public boolean isCanceled() {
        return canceled.get();
    }

}
