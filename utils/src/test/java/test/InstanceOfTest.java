package test;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/6/21 15:57
 */
public class InstanceOfTest {

    @Test
    public void instance() {
        ArrayList<Object> list = new ArrayList<>();
        System.out.println(list instanceof List);
        System.out.println(List.class.isInstance(list));
        System.out.println(list.getClass().isAssignableFrom(List.class));

        // this class is the same as the giving class or the super-class of it.
        System.out.println(List.class.isAssignableFrom(List.class));
        System.out.println(List.class.isAssignableFrom(ArrayList.class));
    }

    @Test
    public void primitive() {
        int x = 1;
        // 此方法只针对 non-primitive 类型
        // primitive 类型均为 false
        System.out.println(int.class.isInstance(x));
    }

}
