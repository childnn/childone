package onjava8;

import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/10 21:33
 */
public class Pizza {
    private final int id;
    private Step step = Step.DOUGH;

    public Pizza(int id) {
        this.id = id;
    }

    public Pizza next() {
        step = step.forward();
        System.out.println("Pizza " + id + ": " + step);
        return this;
    }

    public Pizza next(Step previousStep) {
        if (!step.equals(previousStep))
            throw new IllegalStateException("Expected " + previousStep + " but found " + step);
        return next();
    }

    public Pizza roll() {
        return next(Step.DOUGH);
    }

    public Pizza sauce() {
        return next(Step.ROLLED);
    }

    public Pizza cheese() {
        return next(Step.SAUCED);
    }

    public Pizza toppings() {
        return next(Step.CHEESED);
    }

    public Pizza bake() {
        return next(Step.TOPPED);
    }

    public Pizza slice() {
        return next(Step.BAKED);
    }

    public Pizza box() {
        return next(Step.SLICED);
    }

    public boolean complete() {
        return step.equals(Step.BOXED);
    }

    @Override
    public String toString() {
        return "Pizza" + id + ": " + (step.equals(Step.BOXED) ? "complete" : step);
    }

    public enum Step {
        DOUGH(4), ROLLED(1), SAUCED(1), CHEESED(2),
        TOPPED(5), BAKED(2), SLICED(1), BOXED(0);
        int effort; // Needed to get to the next step

        Step(int effort) {
            this.effort = effort;
        }

        Step forward() {
            if (equals(BOXED))
                return BOXED;
            new Nap(effort * 0.1);
            return values()[ordinal() + 1];
        }
    }
}

class OnePizza {
    public static void main(String[] args) {
        Pizza za = new Pizza(0);
        System.out.println(Timer.duration(() -> {
            while (!za.complete())
                za.next();
        }));
    }
}

class PizzaStreams {
    static final int QUANTITY = 5;

    public static void main(String[] args) {
        Timer timer = new Timer();
        IntStream.range(0, QUANTITY)
                .mapToObj(Pizza::new)
                .parallel() // [1] 注释看时间
                .forEach(za -> {
                    while (!za.complete())
                        za.next();
                });
        System.out.println(timer.duration());
    }
}

class PizzaParallelSteps {
    static final int QUANTITY = 5;

    public static void main(String[] args) {
        Timer timer = new Timer();
        IntStream.range(0, QUANTITY)
                .mapToObj(Pizza::new)
                .parallel()
                .map(Pizza::roll)
                .map(Pizza::sauce)
                .map(Pizza::cheese)
                .map(Pizza::toppings)
                .map(Pizza::bake)
                .map(Pizza::slice)
                .map(Pizza::box)
                .forEach(System.out::println);
        System.out.println(timer.duration());
    }
}