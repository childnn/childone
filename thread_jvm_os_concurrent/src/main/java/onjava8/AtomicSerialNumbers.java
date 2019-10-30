package onjava8;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/7 18:06
 */
public class AtomicSerialNumbers extends SerialNumbers {

    private AtomicInteger serialNumber = new AtomicInteger();

    public static void main(String[] args) {
        SerialNumberChecker.test(new AtomicSerialNumbers());
    }

    public synchronized int nextSerialNumber() {
        return serialNumber.getAndIncrement();
    }

}