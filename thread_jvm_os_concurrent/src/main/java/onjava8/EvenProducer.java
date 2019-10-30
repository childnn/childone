package onjava8;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/6 19:29
 */
public class EvenProducer extends IntGenerator {

    private int currentEvenValue = 0;

    public static void main(String[] args) {
        EvenChecker.test(new EvenProducer());
    }

    @Override
    public int next() {
        ++currentEvenValue; // [1]
        ++currentEvenValue;
        return currentEvenValue;
    }

}
