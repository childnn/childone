package onjava8.patterns.templatemethod;

import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/22 12:48
 */
public class TemplateMethod {

    public static void main(String[] args) {
        new MyApp();
    }

}

abstract class ApplicationFramework {

    ApplicationFramework() {
        templateMethod();
    }

    abstract void customize1();

    abstract void customize2();

    // "private" means automatically "final":
    private void templateMethod() {
        IntStream.range(0, 5).forEach(n -> {
            customize1();
            customize2();
        });
    }

}

// Create a new "application":
class MyApp extends ApplicationFramework {

    @Override
    void customize1() {
        System.out.print("Hello ");
    }

    @Override
    void customize2() {
        System.out.println("World!");
    }

}