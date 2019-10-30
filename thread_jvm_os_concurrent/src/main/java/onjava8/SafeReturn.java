package onjava8;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/7 15:57
 */
public class SafeReturn extends IntTestable {
    private int i = 0;

    public static void main(String[] args) {
        Atomicity.test(new SafeReturn());
    }

    public synchronized int getAsInt() {
        return i;
    }

    public synchronized void evenIncrement() {
        i++;
        i++;
    }
}