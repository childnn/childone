package jvm.array;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/17 15:14
 */
public class ComponentType {
    public static void main(String[] args) {
        int[][][] arr = new int[0][0][0];
        Class<?> componentType = arr.getClass().getComponentType();
        // 三维数组的组件类型是 二维数组
        System.out.println("componentType = " + componentType);
        String typeName = arr.getClass().getTypeName();
        System.out.println("typeName = " + typeName);
    }
}
