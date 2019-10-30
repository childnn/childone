/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/16 15:36
 */
public class MyTest {
    public static void main(String[] args) {
        int b = "a".compareTo("b");
        System.out.println("b = " + b);
        Class<?> componentType = Class.class.getComponentType();
        System.out.println("componentType = " + componentType);

        Class<?> componentType1 = args.getClass().getComponentType(); // 返回 数组的组件类型, 非数组返回 null.
        System.out.println("componentType1 = " + componentType1);

    }

}
