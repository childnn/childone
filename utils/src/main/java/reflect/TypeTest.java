package reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/03/18
 */
public class TypeTest {

    public static void main(String[] args) {
        Field[] fs = GT.class.getDeclaredFields();
        for (Field f : fs) {
            Class<?> type = f.getType();
            // 看源码: 只有在 array 时, 以下俩方法返回结果不同
            System.out.println("type.getName() = " + type.getName());
            System.out.println("type.getTypeName() = " + type.getTypeName());

            Type genericType = f.getGenericType();
            System.out.println("genericType.getTypeName() = " + genericType.getTypeName());

            System.out.println();
        }
    }

}

class GT<T, X> {
    private String a;
    private List<String> list;
    private Collection<T> collection;
    private Map<T, X> map;
    private T t;


}
