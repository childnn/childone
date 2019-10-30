package test;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/2/5 10:17
 */
public class ListTest {
    @Test
    public void test() {
        List<Student> list = Collections.singletonList(new Student());
        f(list);
        System.out.println("list = " + list);
    }

    private void f(List<Student> list) {
        list.get(0).setName("jack");
    }
}
