package onjava8.patterns.facade;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see onjava8.patterns.adapt.Adapter
 * 当我想方设法试图将需求初步（first-cut）转化成对象的时候，通常我使用的原则是：
 * “把所有丑陋的东西都隐藏到对象里去”。
 * 基本上说，*外观模式* 干的就是这个事情。如果我们有一堆让人头晕的类以及交互（Interactions），
 * 而它们又不是客户端程序员必须了解的，那我们就可以为客户端程序员创建一个接口只提供那些必要的功能。
 * 外观模式经常被实现为一个符合单例模式（Singleton）的抽象工厂（abstract factory）。
 * 当然，你可以通过创建包含 **静态** 工厂方法（static factory methods）的类来达到上述效果。
 * ---
 * *外观模式* 更倾向于“过程式的（procedural）”，也就是非面向对象的（non-object-oriented）：
 * 我们是通过调用某些函数才得到对象。它和抽象工厂（Abstract factory）到底有多大差别呢？
 * *外观模式* 关键的一点是隐藏某个库的一部分类（以及它们的交互），使它们对于客户端程序员不可见，这样那些类的接口就更加简练和易于理解了。
 * 其实，这也正是 Java 的 packaging（包）的功能所完成的事情：在库以外，我们只能创建和使用被声明为公共（public）的那些类；
 * 所有非公共（non-public）的类只能被同一 package 的类使用。看起来，*外观模式* 似乎是 Java 内嵌的一个功能。
 * @since 2020/3/23 20:52
 */
public class Facade {

    static A makeA(int x) {
        return new A(x);
    }

    static B makeB(long x) {
        return new B(x);
    }

    static C makeC(double x) {
        return new C(x);
    }

    // Other classes that aren't exposed by the
    // facade go here ...
    public static void main(String[] args) {
        // The client programmer gets the objects
        // by calling the static methods:
        A a = Facade.makeA(1);
        B b = Facade.makeB(1);
        C c = Facade.makeC(1.0);
    }
}


class A {
    A(int x) {
    }
}

class B {
    B(long x) {
    }
}

class C {
    C(double x) {
    }
}