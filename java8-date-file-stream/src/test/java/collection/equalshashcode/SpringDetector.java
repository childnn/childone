package collection.equalshashcode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/26 18:12
 */
public class SpringDetector {
    public static <T extends Groundhog> void detectSpring(Class<T> type) {
        try {
            Constructor<T> ghog = type.getConstructor(int.class);
            Map<Groundhog, Prediction> map =
                    IntStream.range(0, 10).mapToObj(i -> {
                        try {
                            return ghog.newInstance(i);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                            .collect(Collectors.toMap(
                                    Function.identity(),
                                    gh -> new Prediction()));
            map.forEach((k, v) -> System.out.println(k + ": " + v));
            Groundhog gh = ghog.newInstance(3);
            System.out.println("Looking up prediction for " + gh);
            if (map.containsKey(gh))
                System.out.println(map.get(gh));
            else
                System.out.println("Key not found: " + gh);
        } catch (NoSuchMethodException |
                IllegalAccessException |
                InvocationTargetException |
                InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        detectSpring(Groundhog.class);
    }
}

class SpringDetector2 {
    public static void main(String[] args) {
        SpringDetector.detectSpring(Groundhog2.class);
    }
}

class Prediction {
    private static Random rand = new Random(47);

    @Override
    public String toString() {
        return rand.nextBoolean() ?
                "Six more weeks of Winter!" : "Early Spring!";
    }
}

// Looks plausible, but doesn't work as a HashMap key
class Groundhog {
    protected int number;

    public Groundhog(int n) {
        number = n;
    }

    @Override
    public String toString() {
        return "Groundhog #" + number;
    }

}

// 因为默认的Object.equals()只是比较对象的地址，所以一个Groundhog(3)并不等于另一个Groundhog(3)，
// 因此，如果要使用自己的类作为HashMap的键，必须同时重写hashCode()和equals()
class Groundhog2 extends Groundhog {
    public Groundhog2(int n) {
        super(n);
    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Groundhog2 &&
                Objects.equals(
                        number, ((Groundhog2) o).number);
    }
}
