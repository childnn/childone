package onjava8.patterns.factorymethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/23 17:16
 * 和之前一样，`create()`方法基于你传递给它的**String**参数生成新的**Shape**s，
 * 但是在这里，它是通过在**HashMap**中查找作为键的**String**来实现的。
 * 返回的值是一个构造器，该构造器用于通过调用`newInstance()`创建新的**Shape**对象。
 * 然而，当你开始运行程序时，工厂的`map`为空。`create()`使用`map`的`computeIfAbsent()`方法来查找
 * 构造器（如果该构造器已存在于`map`中）。如果不存在则使用`load()`计算出该构造器，并将其插入到`map`中。
 * 从输出中可以看到，每种特定类型的**Shape**都是在第一次请求时才加载的，然后只需要从`map`中检索它。
 */
public class ShapeFactory2 implements FactoryMethod {

    Map<String, Constructor<Shape>> factories = new HashMap<>();

    static Constructor load(String id) {
        System.err.println("loading " + id);
        try {
            return Class.forName("onjava8.patterns.factorymethod." + id).getDeclaredConstructor();
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new BadShapeCreation(id);
        }
    }

    public static void main(String[] args) {
        FactoryTest.test(new ShapeFactory2());
    }

    public Shape create(String id) {
        try {
            return factories.computeIfAbsent(id, ShapeFactory2::load).newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BadShapeCreation(id);
        }
    }

}
