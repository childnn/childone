package onjava8;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/6 18:22
 */
public class TestAbort {

    // 如果你注释掉 Nap 创建实列那行，程序执行会立即退出，表明 TimedAbort 没有维持程序打开。
    public static void main(String[] args) {
        new TimedAbort(1);
        System.out.println("Napping for 4");
        new Nap(4);
    }

}
