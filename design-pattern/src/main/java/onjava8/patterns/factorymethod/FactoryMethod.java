package onjava8.patterns.factorymethod;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/23 17:11
 */
public interface FactoryMethod {
    Shape create(String type);
}
