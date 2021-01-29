package org.anonymous.se;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/6 19:26
 */
public class FinalMethod {
    // private 本身意味着不可重写, final 实际为多余
    private final void f1() {

    }

    final void f2() {

    }
}

class FinaleSub extends FinalMethod {

    // ERROR: 不能重写父类 非 private 的 final 方法
    /*void f2() {

    }*/

    // 于父类方法同名, 但父类 private 修饰, 不算重写
    void f1() {

    }
}
