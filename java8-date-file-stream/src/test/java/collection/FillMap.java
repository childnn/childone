package collection;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/25 10:57
 */
public class FillMap {
    public static <K, V> Map<K, V> basic(Supplier<Pair<K, V>> pairGen, int size) {
        return Stream.generate(pairGen)
                .limit(size)
                .collect(Collectors
                        .toMap(Pair::key, Pair::value));
    }

    public static <K, V> Map<K, V> basic(Supplier<K> keyGen, Supplier<V> valueGen, int size) {
        return Stream.generate(() -> Pair.make(keyGen.get(), valueGen.get()))
                .limit(size)
                .collect(Collectors.toMap(Pair::key, Pair::value));
    }

    public static <K, V, M extends Map<K, V>> M create(Supplier<K> keyGen,
                                                       Supplier<V> valueGen,
                                                       Supplier<M> mapSupplier, int size) {
        return Stream.generate(() ->
                Pair.make(keyGen.get(), valueGen.get()))
                .limit(size)
                .collect(Collectors
                        .toMap(Pair::key, Pair::value,
                                (k, v) -> k, mapSupplier));
    }


    public static void main(String[] args) {
        Map<String, Integer> mcs = FillMap.basic(
                new Rand.String(4), new Count.Integer(), 7);
        System.out.println(mcs);
        HashMap<String, Integer> hashm =
                FillMap.create(new Rand.String(4),
                        new Count.Integer(), HashMap::new, 7);
        System.out.println(hashm);
        LinkedHashMap<String, Integer> linkm =
                FillMap.create(new Rand.String(4),
                        new Count.Integer(), LinkedHashMap::new, 7);
        System.out.println(linkm);
    }

}
