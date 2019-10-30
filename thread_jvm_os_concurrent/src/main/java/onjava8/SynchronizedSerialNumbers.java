package onjava8;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/7 16:51
 */
public class SynchronizedSerialNumbers extends SerialNumbers {
    private int serialNumber = 0;

    public static void main(String[] args) {
        SerialNumberChecker.test(new SynchronizedSerialNumbers());
    }

    public synchronized int nextSerialNumber() {
        return serialNumber++;
    }
}