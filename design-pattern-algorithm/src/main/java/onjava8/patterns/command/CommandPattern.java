package onjava8.patterns.command;

import java.util.Arrays;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/23 19:22
 * 一个 *函数对象* 封装了一个函数。其特点就是将被调用函数的选择与那个函数被调用的位置进行解耦。
 * 从最直观的角度来看，*命令模式* 就是一个函数对象：一个作为对象的函数。
 * 我们可以将 *函数对象* 作为参数传递给其他方法或者对象，来执行特定的操作。
 * ---
 * *命令模式* 的主要特点是允许向一个方法或者对象传递一个想要的动作。
 * 在本例中，这个对象就是 **macro** ，而 *命令模式* 提供了将一系列需要一起执行的动作集进行排队的方法。
 * 在这里，*命令模式* 允许我们动态的创建新的行为，通常情况下我们需要编写新的代码才能完成这个功能，
 * 而在本例中，我们可以通过解释运行一个脚本来完成这个功能（如果需要实现的东西很复杂请参考解释器模式）。
 * ---
 * *《设计模式》* 认为“命令模式是回调的面向对象的替代品”。尽管如此，我认为"back"（回来）
 * 这个词是callback（回调）这一概念的基本要素。也就是说，我认为回调（callback）实际上是返回到回调的创建者所在的位置。
 * 另一方面，对于 *命令* 对象，通常只需创建它并将其交给某种方法或对象，而不是自始至终以其他方式联系命令对象。
 */
public class CommandPattern {

    public static void main(String[] args) {
        List<Runnable> macro = Arrays.asList(
                () -> System.out.print("Hello "),
                () -> System.out.print("World! "),
                () -> System.out.print("I'm the command pattern!")
        );
        macro.forEach(Runnable::run);
    }

}
