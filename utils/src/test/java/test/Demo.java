package test;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/28 17:25
 */
public class Demo {
    @SafeVarargs // unsafe.
    static void m(List<String>... stringLists) {
        Object[] array = stringLists;
        List<Integer> tmpList = Collections.singletonList(42);
        array[0] = tmpList; // Semantically invalid, but compiles without warnings
        String s = stringLists[0].get(0); // Oh no, ClassCastException at runtime!
    }

    @Test
    public void test() {
        int a = 0x20;
        System.out.println("a = " + a);
    }

}
