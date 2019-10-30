package jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/20 10:39
 * VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
 * 期望结果:
 * java.lang.OutOfMemoryError: PermGen Space
 * 说明运行时常量池属于方法区
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {
        // 使用 list 保持常量池引用, 避免 Full GC 回收常量池行为
        List<String> list = new ArrayList<>();
        // 10MB 的 PermSize 在 integer 范围内足够禅城 OOM 了
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }

}
