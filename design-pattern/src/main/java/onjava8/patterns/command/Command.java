package onjava8.patterns.command;

import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/6 20:43
 */
public interface Command {

    static void main(String[] args) {
        int[] arr = {1, 2, 3, 4};
        ProcessArray pa = new ProcessArray();
        pa.process(arr, new PrintCmd());
        pa.process(arr, new AddCmd());

    }

    // 在定义接口时, 并不知道该方法执行怎样的行为
    // 必须等到执行时才能确定
    // 即可变的方法体
    void process(int[] arr);

}

class ProcessArray {
    public void process(int[] arr, Command cmd) {
        cmd.process(arr);
    }
}

class PrintCmd implements Command {

    @Override
    public void process(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }
}

class AddCmd implements Command {

    @Override
    public void process(int[] arr) {
        System.out.println("sum: " + Arrays.stream(arr).sum());
    }
}