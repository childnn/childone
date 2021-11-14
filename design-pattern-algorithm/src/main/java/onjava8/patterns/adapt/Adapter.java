package onjava8.patterns.adapt;

interface WhatIWant {
    void f();
}

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see onjava8.patterns.facade.Facade
 * @since 2020/3/23 20:50
 * <p>
 * ## 改变接口
 * 有时候我们需要解决的问题很简单，仅仅是“我没有需要的接口”而已。
 * 有两种设计模式用来解决这个问题：*适配器模式* 接受一种类型并且提供一个对其他类型的接口。
 * *外观模式* 为一组类创建了一个接口，这样做只是为了提供一种更方便的方法来处理库或资源。
 * ---
 * 当我们手头有某个类，而我们需要的却是另外一个类，我们就可以通过 *适配器模式* 来解决问题。
 * 唯一需要做的就是产生出我们需要的那个类，有许多种方法可以完成这种适配。
 * ---
 * 我想冒昧的借用一下术语“proxy”（代理），因为在 *《设计模式》* 里，
 * 他们坚持认为一个代理（proxy）必须拥有和它所代理的对象一模一样的接口。
 * 但是，如果把这两个词一起使用，叫做“代理适配器（proxy adapter）”，似乎更合理一些。
 */
public class Adapter {
    public static void main(String[] args) {
        WhatIUse whatIUse = new WhatIUse();
        WhatIHave whatIHave = new WhatIHave();
        WhatIWant adapt = new ProxyAdapter(whatIHave);
        whatIUse.op(adapt);
        // Approach 2:
        WhatIUse2 whatIUse2 = new WhatIUse2();
        whatIUse2.op(whatIHave);
        // Approach 3:
        WhatIHave2 whatIHave2 = new WhatIHave2();
        whatIUse.op(whatIHave2);
        // Approach 4:
        WhatIHave3 whatIHave3 = new WhatIHave3();
        whatIUse.op(whatIHave3.whatIWant());
    }
}

class WhatIHave {
    public void g() {
    }

    public void h() {
    }
}

class ProxyAdapter implements WhatIWant {
    WhatIHave whatIHave;

    ProxyAdapter(WhatIHave wih) {
        whatIHave = wih;
    }

    @Override
    public void f() {
        // Implement behavior using
        // methods in WhatIHave:
        whatIHave.g();
        whatIHave.h();
    }
}

class WhatIUse {
    public void op(WhatIWant wiw) {
        wiw.f();
    }
}

// Approach 2: build adapter use into op():
class WhatIUse2 extends WhatIUse {
    public void op(WhatIHave wih) {
        new ProxyAdapter(wih).f();
    }
}

// Approach 3: build adapter into WhatIHave:
class WhatIHave2 extends WhatIHave implements WhatIWant {
    @Override
    public void f() {
        g();
        h();
    }
}

// Approach 4: use an inner class:
class WhatIHave3 extends WhatIHave {
    public WhatIWant whatIWant() {
        return new InnerAdapter();
    }

    private class InnerAdapter implements WhatIWant {
        @Override
        public void f() {
            g();
            h();
        }
    }
}
