package collection;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/24 20:09
 */
public class StreamFillMaps {
    public static void main(String[] args) {
        Map<Integer, String> m =
                Stream.generate(new Letters())
                        .limit(11)
                        .collect(Collectors.toMap(Pair::key, Pair::value));
        System.out.println(m);

        // Two separate Suppliers:
        Rand.String rs = new Rand.String(3);
        Count.Character cc = new Count.Character();
        Map<Character, String> mcs = Stream.generate(() -> Pair.make(cc.get(), rs.get()))
                .limit(8)
                .collect(Collectors.toMap(Pair::key, Pair::value));
        System.out.println(mcs);

        // A key Supplier and a single value:
        Map<Character, String> mcs2 = Stream.generate(
                () -> Pair.make(cc.get(), "Val"))
                .limit(8)
                .collect(Collectors.toMap(Pair::key, Pair::value));
        System.out.println(mcs2);
    }
}

class Letters implements Supplier<Pair<Integer, String>> {

    private int number = 1;
    private char letter = 'A';

    @Override
    public Pair<Integer, String> get() {
        return new Pair<>(number++, "" + letter++);
    }

}