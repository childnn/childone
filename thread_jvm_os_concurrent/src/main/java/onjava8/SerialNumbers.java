package onjava8;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/7 16:49
 */
public class SerialNumbers {
    private volatile int serialNumber = 0;

    public int nextSerialNumber() {
        return serialNumber++; // Not thread-safe
    }
}
