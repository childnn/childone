package org.anonymous.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/2/2 10:09
 */
public class CompileClassLoader extends ClassLoader {

    /**
     * 读取文件内容
     * @param fileName
     * @return
     */
    byte[] getBytes(String fileName) throws IOException {
        final File file = new File(fileName);
        final long len = file.length();
        final byte[] raw = new byte[(int) len];
        final FileInputStream is = new FileInputStream(fileName);

        // 一次读取 Class 文件的全部二进制数据
        final int r = is.read(raw);
        if (r != len) {
            System.out.printf("读取全部文件失败! len = %s, r = %s", len, r);
            return null;
        }
        return raw;
    }

    // 定义编译指定 Java 文件的方法
    boolean compile(String javaFile) throws InterruptedException, IOException {
        System.out.println("CompileClassLoader-正在编译: " + javaFile + "...");
        // 调用系统的 javac 命令
        final Process process = Runtime.getRuntime().exec("javac " + javaFile);
        // 其他线程都等待这个线程完成
        process.waitFor();

        // 获取 javac 线程退出值
        final int ret = process.exitValue();
        // 返回编译成功与否
        return ret == 0;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        final String fileName = name.replace(".", "/");
        final String javaFileName = fileName + ".java";
        final String classFileName = fileName + ".class";

        final File jFile = new File(javaFileName);
        final File cFile = new File(classFileName);

        // 修改 Java 源文件存在, 且 Class 文件不存在或者 Java 源文件的修改时间比 Class 文件的修改时间更晚时, 重新编译
        if (jFile.exists() &&
                (!cFile.exists() || jFile.lastModified() > cFile.lastModified())) {
            // 如果编译失败, 或者该 Class 文件不存在
            try {
                if (!compile(javaFileName) || !cFile.exists()) {
                    throw new ClassNotFoundException("ClassNotFoundException: " + javaFileName);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Class clazz = null;
        // 如果 Class 文件存在, 系统负责将该文件转换成 Class 对象
        if (cFile.exists()) {
            try {
                // 将 Class 文件的二进制数据读入数组
                final byte[] raw = getBytes(classFileName);
                // 调用 ClassLoader 的 defineClass 方法将二进制数据转换成 Class 对象
                clazz = defineClass(name, raw, 0, raw.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 如果 class 为 null, 表明加载失败
        if (clazz == null) {
            throw new ClassNotFoundException(name);
        }
        return clazz;
    }

    public static void main(String[] args)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 如果运行该程序时没有参数, 即没有目标类
        if (args.length < 1) {
            System.out.println("缺少目标类, 请按如下格式运行 Java 源文件: ");
            System.out.println("java CompileClassLoader ClassName");
        }

        System.out.println("参数: " + Arrays.asList(args));

        // 第一个参数是需要运行的类
        String processClass = args[0];
        // 剩下的参数将作为运行时目标类时的参数
        // 将这些参数复制到一个新数组中
        final String[] processArgs = new String[args.length - 1];
        System.arraycopy(args, 1, processArgs, 0, processArgs.length);

        final CompileClassLoader ccl = new CompileClassLoader();
        // 加载需要运行的类
        final Class<?> clazz = ccl.loadClass(processClass);

        // 反射: 获取 目标类的 main 方法
        final Method main = clazz.getMethod("main", args.getClass());
        Object[] argsArr = {processArgs};

        main.invoke(null, argsArr);
    }

}
