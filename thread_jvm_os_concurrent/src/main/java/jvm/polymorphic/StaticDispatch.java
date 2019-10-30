package jvm.polymorphic;

import java.io.Serializable;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/8 21:01
 * 静态分派: Dispatch.
 * 英文技术文档的称呼是 Method Overload Resolution, 但国内各种资料都普遍将这种行为翻译成 "静态分派".
 */
public class StaticDispatch {
    public static void main(String[] args) {
        // 代码右侧: Human 称为变量的 静态类型(Static Type), 或者叫做外观类型(Apparent Type),
        // 左侧的 "Man" 则称为变量的实际类型(Actual Type), 静态类型和实际类型在程序中都可以发生一些变化,
        // 区别是静态类型的变化仅仅在使用时发生,变量本身的静态类型不会被改变,并且最终的静态类型是在编译器可知的,
        // 而实际类型变化的结果在运行期才可确定,编译器在编译程序的时候并不知道一个对象的实际类型是什么.
        // 以下两行代码,静态类型相同,但实际类型不同, 但虚拟机(准确的说是编译器)在重载时是通过参数的静态类型
        // 而不是实际类型作为判定依据的. 并且静态类型是编译期可知的, 因此, 在编译阶段, Javac 编译器会根据参数的静态类型
        // 决定使用哪个重载版本,所以选择了 sayHello(Human) 作为调用的目标,并把这个方法的符号引用写到 main() 方法里
        // 的两条 invokevirtual 指令的参数中.
        // 所有依赖静态类型来定位方法执行版本的分派动作称为 静态分派.
        // 静态分派的典型应用是方法重载. 静态分派发生在 编译阶段, 因此确定静态分派的动作实际上不是由虚拟机来执行的.
        // 另外, 编译器虽然能确定出方法的重载版本,但在很多情况下这个重载版本并不是 "唯一的", 往往只能确定一个 "更加适合的"
        // 版本. 产生这种模糊结论的主要原因是字面量不需要定义,所以字面量没有显式的静态类型,它的静态类型只能通过语言上
        // 的规则去理解和推断.
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatch sd = new StaticDispatch();
        sd.sayHello(man);
        sd.sayHello(woman);
    }

    public void sayHello(Human gut) {
        System.out.println("hello guy!");
    }

    public void sayHello(Man man) {
        System.out.println("hello gentleman!");
    }

    public void sayHello(Woman woman) {
        System.out.println("hello lady!");
    }

    static abstract class Human {

    }

    static class Man extends Human {

    }

    static class Woman extends Human {
    }
}

class OverLoad {
    static void sayHello(Object arg) {
        System.out.println("hello Object");
    }

    static void sayHello(int arg) {
        System.out.println("hello int");
    }

    static void sayHello(long arg) {
        System.out.println("hello long");
    }

    static void sayHello(Character arg) {
        System.out.println("hello Character");
    }

    static void sayHello(char arg) {
        System.out.println("hello char");
    }

    static void sayHello(char... arg) {
        System.out.println("hello char...");
    }

    static void sayHello(Serializable arg) {
        System.out.println("hello Serializable");
    }

    //@SuppressWarnings("rawtypes")
    static void sayHello(Comparable<Character> arg) {
        System.out.println("hello Comparable");
    }

    // 一次注释各个方法: char -> int -> long -> float -> double -- 安全的转换
    // --> Character --> Serializable
    public static void main(String[] args) {
        // 如果只保留 参数位 Serializable 和 Comparable 的两个方法
        // 则此处无法通过编译: 因为 Character 实现了 Comparable 和 Serializable 接口
        // 它们的的优先级是一样的, 要想通过运行, 此处必须强制转换
        // 越往上层,优先级越低, Object 参数注释掉后, 边长参数才会调用
        // 此时 'a' 被当成只有一个元素的数组
        sayHello(/*(*//*Serializable*//*Comparable<Character>)*/ 'a');
    }
}
