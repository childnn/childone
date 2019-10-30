package org.anonymous.lombok.sync;

import lombok.Synchronized;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/11 15:16
 */
public class SynchronizedDemo {

    private final Object readLock = new Object();

    @Synchronized
    public static void hello() {
        System.out.println("world!");
    }

    @Synchronized
    public int answerToLife() {
        return 42;
    }

    @Synchronized
    public void foo() {
        System.out.println("bar");
    }

    /*    private static final Object $LOCK = new Object[0];
    private final Object $lock = new Object[0];
    private final Object readLock = new Object();

    public SynchronizedDemo() {
    }

    public static void hello() {
        synchronized($LOCK) {
            System.out.println("world!");
        }
    }

    public int answerToLife() {
        synchronized(this.$lock) {
            return 42;
        }
    }

    public void foo() {
        synchronized(this.$lock) {
            System.out.println("bar");
        }
    }*/

}
