package org.anonymous.onjava8;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/7 17:36
 */
public class AtomicEvenProducer extends IntGenerator {
    private final AtomicInteger currentEvenValue = new AtomicInteger(0);

    public static void main(String[] args) {
        EvenChecker.test(new AtomicEvenProducer());
    }

    @Override
    public int next() {
        return currentEvenValue.addAndGet(2);
    }
}