package onjava8.patterns.factorymethod;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/23 17:09
 */
public class Shape {
    private static int counter = 0;
    private int id = counter++;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + id + "]";
    }

    public void draw() {
        System.out.println(this + " draw");
    }

    public void erase() {
        System.out.println(this + " erase");
    }
}

class Circle extends Shape {
}

class Square extends Shape {
}

class Triangle extends Shape {
}