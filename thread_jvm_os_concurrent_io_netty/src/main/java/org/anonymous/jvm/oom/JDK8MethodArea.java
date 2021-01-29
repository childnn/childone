package org.anonymous.jvm.oom;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/7/6 22:11
 */
public class JDK8MethodArea extends ClassLoader {
    /**
     * VM-args: -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M
     * java.lang.OutOfMemoryError: Compressed class space
     */
    public static void main(String[] args) {
        int j = 0;
        try {
            JDK8MethodArea oom = new JDK8MethodArea();
            for (int i = 0; i < 10_000; i++) {
                ClassWriter cw = new ClassWriter(0);
                String className = "Class" + i;
                // 版本,修饰符,类名,包名,全限定名(斜杠分割),接口
                cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, className,
                        "jvm.oom", Type.getType(Object.class).getInternalName(), null);
                byte[] bytes = cw.toByteArray();
                // 类加载
                Class<?> clazz = oom.defineClass(className, bytes, 0, bytes.length);

                j++;
            }
        } finally {
            System.out.println(j);
        }

    }
}
