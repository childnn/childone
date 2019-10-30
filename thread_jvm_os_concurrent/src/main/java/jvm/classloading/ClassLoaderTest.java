package jvm.classloading;

import java.io.IOException;
import java.io.InputStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/7 10:22
 * 比较两个类是否 "相等", 只有在这两个类是由同一个类加载器加载的前提下才有意义,
 * 否则, 即使这两个类来源于同一个 Class 文件, 被同一个虚拟机加载, 只要加载它们的类加载器不同, 那这两个类就必定不相等.
 * 这里所指的 "相等", 包括 代表类的 Class 对象的 equals() 方法, isAssignableFrom() 方法, isInstance() 方法的返回结果,
 * 也包括使用 instanceof 关键字做对象所属关系判定等情况.
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                //return super.loadClass(name);
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Object obj = myLoader.loadClass(ClassLoaderTest.class.getName()).newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof ClassLoaderTest);
    }
}
