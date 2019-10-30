package test;

import org.anonymous.util.CombinationUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/22 11:51
 */
public class CombinationTest {
    @Test
    public void test() {
        Object[] srcArr1 = {"jack", "java", "rose"};
        Object[] srcArr2 = {"j", "a", "r"};
        @SuppressWarnings("serial")
        List<Object[]> dataList = new ArrayList<Object[]>(){{
            add(srcArr1);
            add(srcArr2);
        }};

        List<Object[]> list = CombinationUtil.combination(dataList, 0, null);
        System.out.println(list.size());
        for (Object[] objArr : list) {
            String s = Arrays.toString(objArr);
            System.out.println("s = " + s);
        }
    }
}
