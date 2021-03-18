package org.anonymous.concurrencyinpractice;

import org.anonymous.concurrencyinpractice.annotation.ThreadSafe;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/8 9:17
 * @see org.anonymous.concurrencyinpractice.MutableInteger
 */
@ThreadSafe
public class SynchronizedInteger {
    private int value;

    public synchronized int get() {
        return value;
    }

    public synchronized void set(int value) {
        this.value = value;
    }

}
