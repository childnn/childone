package stream;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/15 13:26
 */
public class MapCollector {
    public static void main(String[] args) {
        Map<Integer, Character> map =
                new RandomPair().stream()
                        .limit(8)
                        .collect(
                                Collectors.toMap(Pair::getI, Pair::getC));
        System.out.println(map);
    }

    @Test
    public void test() {
        HashMap<Object, Object> collect = Stream.of(1, 3, 4)/*.parallel()*/
                .collect(HashMap::new, (m, i) -> m.put(i, "xxx" + i), (m1, m2) -> {
                    System.out.println("m1 = " + m1);
                    System.out.println("m2 = " + m2);
                    m1.putAll(m2);
                });
        System.out.println("collect = " + collect);
    }
}

class RandomPair {
    Random rand = new Random(47);
    // An infinite iterator of random capital letters:
    Iterator<Character> capChars = rand.ints(65, 91)
            .mapToObj(i -> (char) i)
            .iterator();

    public Stream<Pair> stream() {
        return rand.ints(100, 1000).distinct()
                .mapToObj(i -> new Pair(capChars.next(), i));
    }
}

class Pair {
    public final Character c;
    public final Integer i;

    Pair(Character c, Integer i) {
        this.c = c;
        this.i = i;
    }

    public Character getC() {
        return c;
    }

    public Integer getI() {
        return i;
    }

    @Override
    public String toString() {
        return "Pair(" + c + ", " + i + ")";
    }
}

