package jvm.oom;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/21 13:52
 * 在 JDK 1.6 中, intern() 方法会把首次遇到的字符串实例复制到永久代中, 返回的也是永久代
 * 中这个字符串实例的引用, 而由 StringBuilder 创建的字符串实例在 Java 堆上, 所以必然不是同一个引用,
 * 返回 false.
 * 而 JDK 1.7/1.8(以及部分其他虚拟机, 如 JRockit) 的 intern() 实现不会再复制实例, 只是在常量池中记录首次出现的实例
 * 引用, 因此 intern() 返回的引用和由 StringBuilder 创建的那个字符串实例是同一个.
 * 对 str2 比较返回 false 是因为 "java" 这个字符串在执行 StringBuilder.toSting() 之前已经出现过, 字符串常量池中
 * 已经有它的引用了, 不符合 "首次出现" 的原则, 而 "计算机软件" 这个字符串是首次出现, 因此返回 true.
 */
public class StringTest {

    // JDK 1.8: 第一个 true, 第二个 false.
    public static void main(String[] args) {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }

}
