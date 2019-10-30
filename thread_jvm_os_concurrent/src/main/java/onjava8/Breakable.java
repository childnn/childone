package onjava8;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/10 19:08
 */
public class Breakable {
    String id;
    private int failcount;

    public Breakable(String id, int failcount) {
        this.id = id;
        this.failcount = failcount;
    }

    public static Breakable work(Breakable b) {
        if (--b.failcount == 0) {
            System.out.println("Throwing Exception for " + b.id + "");
            throw new RuntimeException("Breakable_" + b.id + " failed");
        }
        System.out.println(b);
        return b;
    }

    @Override
    public String toString() {
        return "Breakable_" + id + " [" + failcount + "]";
    }
}

class CompletableExceptions {
    static CompletableFuture<Breakable> test(String id, int failcount) {
        return
                CompletableFuture.completedFuture(
                        new Breakable(id, failcount))
                        .thenApply(Breakable::work)
                        .thenApply(Breakable::work)
                        .thenApply(Breakable::work)
                        .thenApply(Breakable::work);
    }

    public static void main(String[] args) {
        // Exceptions don't appear ...
        test("A", 1);
        test("B", 2);
        test("C", 3);
        test("D", 4);
        test("E", 5);
        // ... until you try to fetch the value:
        try {
            test("F", 2).get(); // or join()
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Test for exceptions:
        System.out.println(
                test("G", 2).isCompletedExceptionally());
        // Counts as "done":
        System.out.println(test("H", 2).isDone());
        // Force an exception:
        CompletableFuture<Integer> cfi =
                new CompletableFuture<>();
        System.out.println("done? " + cfi.isDone());
        cfi.completeExceptionally(new RuntimeException("forced"));
        try {
            cfi.get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

class CatchCompletableExceptions {
    static void handleException(int failcount) {
        // Call the Function only if there's an
        // exception, must produce same type as came in:
        CompletableExceptions.test("exceptionally", failcount)
                .exceptionally((ex) -> { // Function
                    if (ex == null)
                        System.out.println("I don't get it yet");
                    return new Breakable(ex.getMessage(), 0);
                })
                .thenAccept(str -> System.out.println("result: " + str));
        // Create a new result (recover):
        CompletableExceptions.test("handle", failcount)
                .handle((result, fail) -> { // BiFunction
                    if (fail != null)
                        return "Failure recovery object";
                    else
                        return result + " is good";
                })
                .thenAccept(str -> System.out.println("result: " + str));
        // Do something but pass the same result through:
        CompletableExceptions.test("whenComplete", failcount)
                .whenComplete((result, fail) -> {// BiConsumer
                    if (fail != null)
                        System.out.println("It failed");
                    else
                        System.out.println(result + " OK");
                })
                .thenAccept(r -> System.out.println("result: " + r));
    }

    public static void main(String[] args) {
        System.out.println("**** Failure Mode ****");
        handleException(2);
        System.out.println("**** Success Mode ****");
        handleException(0);
    }
}

class StreamExceptions {
    static Stream<Breakable> test(String id, int failcount) {
        return Stream.of(new Breakable(id, failcount))
                .map(Breakable::work)
                .map(Breakable::work)
                .map(Breakable::work)
                .map(Breakable::work);
    }

    public static void main(String[] args) {
        // No operations are even applied ...
        test("A", 1);
        test("B", 2);
        Stream<Breakable> c = test("C", 3);
        test("D", 4);
        test("E", 5);
        // ... until there's a terminal operation:
        System.out.println("Entering try");
        try {
            c.forEach(System.out::println); // [1]
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}