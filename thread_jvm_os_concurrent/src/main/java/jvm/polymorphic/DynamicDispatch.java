package jvm.polymorphic;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/12 17:01
 */
public class DynamicDispatch {
    /**
     * 与 {@link StaticDispatch} 一样, 虚拟机是如何知道要调用哪个方法的?
     * 显然这里不可能再根据静态类型来决定,因为静态类型同样都是 Human 的两个变量 man 和 woman
     * 在调用 sayHello() 方法时执行了不同的行为,并且变量 man 在两次调用中执行了不同的方法.
     * 导致这个现象的原因很明显,是这个变量的实例类型不同,Java 虚拟机是如何根据实际类型来分派方法执行版本的呢?
     * 字节码中使用到 invokevirtual 指令, 其运行时解析过程大致分为以下步骤:
     * 1) 找到操作数栈顶的第一个元素所指向的对象的实际类型, 记作 C.
     * 2) 如果在类型 C 中找到与常量中的描述符和简单名称都相符的方法,则进行访问权限校验,
     * 如果通过返回这个方法的直接引用,查找过程结束; 如果不通过, 返回 java.lang.IllegalAccessError
     * 3) 否则, 按照继承关系从下往上依次对 C 的各个父类进行第二步的搜索和验证过程.
     * 4) 如果始终没找到合适的方法,则抛出 java.lang.AbstractMethodError.
     * 这种在运行期根据实际类型确定方法执行版本的分派过程称为动态分派
     */
    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        man.sayHello();
        woman.sayHello();
        man = new Woman();
        man.sayHello();
    }

    static abstract class Human {
        protected abstract void sayHello();
    }

    static class Man extends Human {

        @Override
        protected void sayHello() {
            System.out.println("man say hello");
        }
    }

    static class Woman extends Human {
        @Override
        protected void sayHello() {
            System.out.println("woman say hello");
        }
    }

}
