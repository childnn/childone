package thread;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/13 11:04
 */
public class ThreadSequence0 {
    public static void main(String[] args) {
        //模拟执行任务的第一个阶段的执行
        Thread stepOne = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " : 第一阶段任务开始执行");
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " : 第一阶段任务执行结束");
            } catch (InterruptedException e) {
            }
        }, "firstStage");
        stepOne.start();

        //模拟任务第二个阶段的执行
        Thread stepTwo = new Thread(() -> {
            while (!Thread.State.TERMINATED.equals(stepOne.getState())) {
                try {
                    Thread.sleep(100);
                    System.err.println(Thread.currentThread().getName() + " : 我在等待第一阶段任务执行结束");
                } catch (InterruptedException e) {
                }
            }
            System.out.println(Thread.currentThread().getName() + " : 第二阶段任务执行结束");
        }, "secondStage");
        stepTwo.start();
    }

}
