package onjava8;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/9 10:39
 */
public class MutexEvenProducer extends IntGenerator {
    private int currentEvenValue = 0;
    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        EvenChecker.test(new MutexEvenProducer());
    }

    @Override
    public int next() {
        lock.lock();
        try {
            ++currentEvenValue;
            new Nap(0.01); // Cause failure faster
            ++currentEvenValue;
            return currentEvenValue;
        } finally {
            lock.unlock();
        }
    }
}
