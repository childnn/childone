package onjava8;

import java.util.concurrent.CompletableFuture;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/7 16:49
 */
public class SerialNumberChecker implements Runnable {
    private CircularSet serials = new CircularSet(1000);
    private SerialNumbers producer;

    public SerialNumberChecker(SerialNumbers producer) {
        this.producer = producer;
    }

    static void test(SerialNumbers producer) {
        for (int i = 0; i < 10; i++)
            CompletableFuture.runAsync(new SerialNumberChecker(producer));
        new Nap(4, "No duplicates detected");
    }

    @Override
    public void run() {
        while (true) {
            int serial = producer.nextSerialNumber();
            if (serials.contains(serial)) {
                System.out.println("Duplicate: " + serial);
                System.exit(0);
            }
            serials.add(serial);
        }
    }
}

class SerialNumberTest {
    public static void main(String[] args) {
        SerialNumberChecker.test(new SerialNumbers());
    }
}