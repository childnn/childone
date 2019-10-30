package anonymous;

import java.util.ArrayList;
import java.util.Spliterator;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/16 12:33
 */
public class SpliteratorTest1 {
    public static void main(String[] args) {
        // 初始化list
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 20; i++) {
            list.add(i + 1);
        }
        //四线程均分配比方式
        Spliterator<Integer> spliterator01 = list.spliterator();        //01中有20个元素
        Spliterator<Integer> spliterator02 = spliterator01.trySplit();    //01中有10个元素,02中有10个元素
        Spliterator<Integer> spliterator03 = spliterator01.trySplit();    //01中有5个元素,02中有10个元素,03中有5个元素
        Spliterator<Integer> spliterator04 = spliterator02.trySplit();    //01中有5个元素,02中有5个元素,03中有5个元素,04中有5个元素
        MyThread4Spliterator<Integer> t01 = new MyThread4Spliterator<>(spliterator01);
        MyThread4Spliterator<Integer> t02 = new MyThread4Spliterator<>(spliterator02);
        MyThread4Spliterator<Integer> t03 = new MyThread4Spliterator<>(spliterator03);
        MyThread4Spliterator<Integer> t04 = new MyThread4Spliterator<>(spliterator04);
        t01.setName("001");
        t02.setName("002");
        t03.setName("003");
        t04.setName("004");

        t01.start();
        t02.start();
        t03.start();
        t04.start();
    }
}

class MyThread4Spliterator<T> extends Thread {
    // 寄存变量
    private Spliterator<T> list;

    // 构造 - 传递参数
    public MyThread4Spliterator(Spliterator<T> list) {
        setList(list);
    }

    // 线程调用run
    @Override
    public void run() {
        Spliterator<T> list2 = getList();
        list2.forEachRemaining(t -> System.out.println(Thread.currentThread().getName() + " === " + t));
    }

    public Spliterator<T> getList() {
        return list;
    }

    public void setList(Spliterator<T> list) {
        this.list = list;
    }

}