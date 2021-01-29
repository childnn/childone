package org.anonymous.se;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/6 13:34
 */
public class StaticNull {

    public static void main(String[] args) {
        StaticNull su = null;
        // 当通过对象访问类成员时, 系统底层会转换成通过类访问类成员
        test(); // null 调用类成员,不会 NPE
    }

    static void test() {
        System.out.println("null");
    }

}
