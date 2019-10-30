package onjava8;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see SharedConstructorArgument
 * @since 2020/4/10 20:21
 */
public class StaticIDField implements HasID {
    private static int counter = 0;
    private int id = counter++;

    @Override
    public int getID() {
        return id;
    }
}

class IDChecker {
    public static final int SIZE = 100_000;

    public static void test(Supplier<HasID> gen) {
        CompletableFuture<List<Integer>>
                groupA = CompletableFuture.supplyAsync(new MakeObjects(gen)),
                groupB = CompletableFuture.supplyAsync(new MakeObjects(gen));

        groupA.thenAcceptBoth(groupB, (a, b) ->
                // retainAll: java.util.List.retainAll
                System.out.println(Sets.intersection(Sets.newHashSet(a), Sets.newHashSet(b)).size()))
                .join();
    }

    static class MakeObjects implements
            Supplier<List<Integer>> {
        private Supplier<HasID> gen;

        MakeObjects(Supplier<HasID> gen) {
            this.gen = gen;
        }

        @Override
        public List<Integer> get() {
            return Stream.generate(gen)
                    .limit(SIZE)
                    .map(HasID::getID)
                    .collect(Collectors.toList());
        }
    }
}

class TestStaticIDField {

    public static void main(String[] args) {
        IDChecker.test(StaticIDField::new);
    }

}

class GuardedIDField implements HasID {
    private static AtomicInteger counter = new AtomicInteger();

    private int id = counter.getAndIncrement();

    public static void main(String[] args) {
        IDChecker.test(GuardedIDField::new);
    }

    public int getID() {
        return id;
    }
}