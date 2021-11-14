package test;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/10 9:18
 */
public class ReflectTest {

    @Test
    public void test() throws IllegalAccessException {
        int limit = 2;
        Data result = new Data();
        Class clazz = result.getClass();
        Field[] dfs = clazz.getDeclaredFields();

        List<String> data = Stream.generate(() -> UUID.randomUUID().toString())
                .limit(limit)
                .collect(Collectors.toList());

        for (int i = 0; i < data.size(); i++) {
            Field f = dfs[i];
            if (!f.isAccessible()) {
                f.setAccessible(true);
            }
            f.set(result, data.get(i));
        }

        System.out.println("result = " + result);

    }
}

class Data {
    private String a;
    private String a1;
    private String a2;
    private String a3;
    private String a4;
    private String a5;

    @Override
    public String toString() {
        return "Data{" +
                "a='" + a + '\'' +
                ", a1='" + a1 + '\'' +
                ", a2='" + a2 + '\'' +
                ", a3='" + a3 + '\'' +
                ", a4='" + a4 + '\'' +
                ", a5='" + a5 + '\'' +
                '}';
    }
}