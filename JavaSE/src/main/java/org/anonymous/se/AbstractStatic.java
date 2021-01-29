package org.anonymous.se;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/6 20:29
 * abstract 的定义就是为了被重写, 因此不可与 private 同用
 */
public class AbstractStatic {
    // abstract 与 static 同时存在的情况: 内部类
    abstract static class AbsStaticInner {

    }
}
