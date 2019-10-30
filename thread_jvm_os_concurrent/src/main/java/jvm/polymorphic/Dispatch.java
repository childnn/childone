package jvm.polymorphic;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/10 9:59
 * 方法的接收者与方法的参数统称为方法的宗量,这个定义最早应该来源于《Java与模式》一书。
 * 根据分派基于多少种宗量，可以将分派划分为单分派和多分派两种。
 * 单分派是根据一个宗量对目标方法进行选择，多分派则是根据多于一个宗量对目标方法进行选择。
 * 在 main 函数中调用了两次 hardChoice() 方法. 编译阶段的选择过程,即静态分派的过程.
 * 这时选择目标方法的依据有两点: 一是静态类型是 Father 还是 Son, 二是方法参数是 QQ 还是 360.
 * 这次选择结果的最终产物是产生了两条 invokevirtual 指令,两条指令的参数分别为常量池中指向
 * Father.hardChoice(360) 及 Father.hardChoice(QQ) 方法的符号引用. 因为是根据两个宗量进行选择,
 * 所以 Java 语言的静态分派属于多分派类型.
 * 再看看运行阶段虚拟机的选择,也就是动态分派的过程. 在执行 son.hardChoice(new QQ()) 这句代码时,
 * 更准确的说，是在执行这句代码所对应的 invokevirtual 指令时，由于编译期已经决定目标方法的签名必须为
 * hardChoice(QQ), 虚拟机此时不会关心传递过来的参数 "QQ" 具体是指什么 QQ, 因为这时参数的静态类型,实际类型
 * 都对方法的选择不会构成任何影响,唯一可以影响虚拟机选择的因素只有此方法的接收者的实际类型是 Father 还是 Son.
 * 因为只有一个宗量作为选择依据,所以 Java 语言的动态分派属于单分派类型.
 */
public class Dispatch {
    public static void main(String[] args) {
        Father father = new Father();
        Son son = new Son();
        father.hardChoice(new _360());
        son.hardChoice(new QQ());
    }

    static class QQ {

    }

    static class _360 {
    }

    static class Father {
        void hardChoice(QQ arg) {
            System.out.println("father choose QQ");
        }

        void hardChoice(_360 arg) {
            System.out.println("father choose 360");
        }
    }

    static class Son extends Father {
        @Override
        void hardChoice(QQ arg) {
            System.out.println("son choose qq");
        }

        @Override
        void hardChoice(_360 arg) {
            System.out.println("son choose 360");
        }
    }

}
