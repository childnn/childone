package org.anonymous.concurrencyinpractice;

import org.anonymous.concurrencyinpractice.annotation.NotThreadSafe;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/8 9:17
 * @see org.anonymous.concurrencyinpractice.SynchronizedInteger
 */
@NotThreadSafe
public class MutableInteger {

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
