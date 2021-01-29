package org.anonymous.thread.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/25 19:08
 */
public class MyRecursiveAction extends RecursiveAction {

    private long workLoad = 0;

    public MyRecursiveAction(long workLoad) {
        this.workLoad = workLoad;
    }

    public static void main(String[] args) {
        //创建了一个并行级别为 4 的 ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);

        //创建一个没有返回值的任务
        MyRecursiveAction myRecursiveAction = new MyRecursiveAction(240);
        //ForkJoinPool执行任务
        forkJoinPool.invoke(myRecursiveAction);
    }

    @Override
    protected void compute() {

        //if work is above threshold, break tasks up into smaller tasks
        //翻译：如果工作超过门槛，把任务分解成更小的任务
        if (this.workLoad > 16) {
            System.out.println("Splitting workLoad : " + this.workLoad);

            List<MyRecursiveAction> subtasks = new ArrayList<>(createSubtasks());

            for (RecursiveAction subtask : subtasks) {
                subtask.fork();
            }

        } else {
            System.out.println("Doing workLoad myself: " + this.workLoad);
        }
    }

    private List<MyRecursiveAction> createSubtasks() {
        List<MyRecursiveAction> subtasks = new ArrayList<>();

        MyRecursiveAction subtask1 = new MyRecursiveAction(this.workLoad / 2);
        MyRecursiveAction subtask2 = new MyRecursiveAction(this.workLoad / 2);

        subtasks.add(subtask1);
        subtasks.add(subtask2);

        return subtasks;
    }

}
