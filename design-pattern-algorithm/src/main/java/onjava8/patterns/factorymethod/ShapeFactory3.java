package onjava8.patterns.factorymethod;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

interface PolymorphicFactory {
    Shape create();
}

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/23 18:27
 * 多态工厂
 */
public class ShapeFactory3 {
    public static void main(String[] args) {
        RandomShapes rs = new RandomShapes(
                /*new PolymorphicFactory() {
                    @Override
                    public Shape create() {
                        return new Shape();
                    }
                },*/
                Circle::new,
                Square::new,
                Triangle::new);

        Stream.generate(rs)
                .limit(6)
                .peek(Shape::draw)
                .peek(Shape::erase)
                .count();
    }
}

class RandomShapes implements Supplier<Shape> {
    private final PolymorphicFactory[] factories;
    private Random rand = new Random(42);

    RandomShapes(PolymorphicFactory... factories) {
        this.factories = factories;
    }

    public Shape get() {
        return factories[rand.nextInt(factories.length)].create();
    }
}