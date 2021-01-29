package test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/17 16:27
 */
public class Demo {

    private static final List<String> list = new ArrayList<>();
    static{
        list.add("1");
        list.add("1");
        list.add("1");
    }


    @org.junit.Test
    public void test() {
        String join = StringUtils.join(list, ",");
        System.out.println(join);
    }

    @Test
    public void test2() {
        String join = StringUtils.join(list, ",");
        System.out.println(join);
    }

}
