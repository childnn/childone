package jvm.classloading;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/30 9:30
 * E:\dev-code\WorkSpace\child\thread_jvm\src\main\resources\jvm.txt
 * 中说明的 5 种 类初始化的触发场景, 称为对一个类进行 主动引用.
 * 除此之外, 所有引用类的方式都不会触发初始化, 称为被动引用.
 */
public class SuperClass {
    static int value = 12;

    static {
        System.out.println("Super");
    }
}

class SubClass extends SuperClass {
    static {
        System.out.println("SubClass");
    }
}

// 被动引用之一
// 对于静态字段, 只有直接定义这个字段的类才会初始化.
// 因此通过其子类来引用父类中定义的静态字段, 只会触发父类的初始化而不会触发子类的初始化.
class NotInitialization {
    public static void main(String[] args) {
        System.out.println(SubClass.value);
    }
}