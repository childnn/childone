package test;

import bridge.SubClass;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/7/3 10:57
 */
public class BridgeMethodTest {

    @Test
    void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SubClass subClass = new SubClass();
        Class<SubClass> subClazz = SubClass.class;
        Method print = subClazz.getMethod("print", String.class);
        print.invoke(subClass, "implementation");
        System.out.println(print.toGenericString() + /*Arrays.asList(print.getParameterTypes()) +*/ " bridge method ? " + print.isBridge());
        System.out.println(print);

        Method printBridge = subClazz.getMethod("print", Object.class);
        printBridge.invoke(subClass, "bridge");

        System.out.println(printBridge.toGenericString() + /*Arrays.asList(printBridge.getParameterTypes()) +*/ " bridge method ? " + printBridge.isBridge());


    }

    @Test
    void flatMap() {
        FlatMap fm = new FlatMap();
        fm.setList(Collections.emptyList());
        FlatMap fm1 = new FlatMap();

        List<String> collect = Stream.of(fm, fm1)
                .flatMap(flatMap -> Optional.ofNullable(flatMap.getList()).orElse(Collections.emptyList()).stream())
                .collect(Collectors.toList());
        System.out.println("collect = " + collect);
    }

    @Data
    static class FlatMap {
        List<String> list;
    }

}
