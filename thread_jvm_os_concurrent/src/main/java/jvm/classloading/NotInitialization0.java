package jvm.classloading;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/30 9:36
 * 通过数组定义来引用类, 不会触发此类的初始化.
 * 被动引用之二
 */
public class NotInitialization0 {
    public static void main(String[] args) {
        SuperClass[] sca = new SuperClass[10];
    }
}
