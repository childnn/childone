package onjava8;

import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/7 16:49
 */
public class CircularSet {
    private int[] array;
    private int size;
    private int index = 0;

    public CircularSet(int size) {
        this.size = size;
        array = new int[size];
        // Initialize to a value not produced
        // by SerialNumbers:
        Arrays.fill(array, -1);
    }

    public synchronized void add(int i) {
        array[index] = i;
        // Wrap index and write over old elements:
        index = ++index % size;
    }

    public synchronized boolean contains(int val) {
        for (int i = 0; i < size; i++)
            if (array[i] == val) return true;
        return false;
    }
}
