package onjava8.patterns.factorymethod;

import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/23 17:11
 */
public class FactoryTest {
    public static void test(FactoryMethod factory) {
        Stream.of("Circle", "Square", "Triangle",
                "Square", "Circle", "Circle", "Triangle")
                // 构建对象
                .map(factory::create)
                .peek(Shape::draw)
                .peek(Shape::erase)
                .count(); // Terminal operation
    }
}
