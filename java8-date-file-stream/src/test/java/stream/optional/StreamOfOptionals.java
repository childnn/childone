package stream.optional;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/15 13:10
 */
public class StreamOfOptionals {

    public static void main(String[] args) {
        Signal.stream()
                .limit(10)
                .forEach(System.out::println);
        System.out.println(" ---");
        Signal.stream()
                .limit(10)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(System.out::println);
    }

}


class Signal {

    static Random rand = new Random(47);
    private final String msg;

    public Signal(String msg) {
        this.msg = msg;
    }

    public static Signal morse() {
        switch (rand.nextInt(4)) {
            case 1:
                return new Signal("dot");
            case 2:
                return new Signal("dash");
            default:
                return null;
        }
    }

    public static Stream<Optional<Signal>> stream() {
        return Stream.generate(Signal::morse)
                .map(Optional::ofNullable);
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "Signal(" + msg + ")";
    }
}