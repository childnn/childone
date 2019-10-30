package onjava8.patterns.factorymethod;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/23 17:12
 * `create()`现在是添加新类型的Shape时系统中唯一需要更改的其他代码。
 * **静态**`create()`方法强制所有创建操作都集中在一个位置，因此这是添加新类型的**Shape**时唯一必须更改代码的地方
 */
public class ShapeFactory1 implements FactoryMethod {
    public static void main(String[] args) {
        FactoryTest.test(new ShapeFactory1());
    }

    // 静态工厂
    public Shape create(String type) {
        switch (type) {
            case "Circle":
                return new Circle();
            case "Square":
                return new Square();
            case "Triangle":
                return new Triangle();
            default:
                throw new BadShapeCreation(type);
        }
    }
}
