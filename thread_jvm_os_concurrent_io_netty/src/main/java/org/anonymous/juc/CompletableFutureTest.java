package org.anonymous.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/9/24 21:17
 */
public class CompletableFutureTest {
    private static String get() {
        System.out.println("Begin Invoke getFuntureHasReturnLambda");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        System.out.println("End Invoke getFuntureHasReturnLambda");
        return "hasReturnLambda";
    }

    public static CompletableFuture<String> getTaskOne() {
        return CompletableFuture.supplyAsync(() -> "topOne");
    }

    public static CompletableFuture<String> getTaskTwo(String s) {
        return CompletableFuture.supplyAsync(() -> s + "  topTwo");
    }

    /**
     * 交换 test 中 sleep 的时间和 get 中 sleep 的时间, 查看结果, 验证 {@link CompletableFuture#complete(java.lang.Object)}
     */
    @Test
    public void test() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f = CompletableFuture.supplyAsync(CompletableFutureTest::get);
        System.out.println("Main Method Is Invoking");
        new Thread(() -> {
            System.out.println("Thread Is Invoking ");
            try {
                Thread.sleep(10000);
                f.complete("custome value"); // 如果 f 没有结束, 则返回 complete 自定义的 value, 否则返回 f 原任务的返回值.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread End ");
        }).start();
        String result = f.get();// blocked
        System.out.println("result = " + result);
        System.out.println("Main Method End");
    }

    /*
    如果有一个异步任务的完成需要依赖前一个异步任务的完成，那么该如何写呢？是调用get()方法获得返回值以后然后再执行吗？
    这样写有些麻烦，CompletableFuture为我们提供了方法来完成我们想要顺序执行一些异步任务的需求。
    thenApply、thenAccept、thenRun这三个方法。这三个方法的区别就是。
        方法名	是否可获得前一个任务的返回值	是否有返回值
        thenApply	能获得	有
        thenAccept	能获得	无
        thenRun	不可获得	无
     */
    @Test
    public void multi() throws ExecutionException, InterruptedException {
        // thenApply  可获取到前一个任务的返回值,也有返回值
        CompletableFuture<String> seqFutureOne = CompletableFuture.supplyAsync(() -> "seqFutureOne");
        CompletableFuture<String> seqFutureTwo = seqFutureOne.thenApply(name -> name + " seqFutureTwo");
        System.out.println(seqFutureTwo.get());


        // thenAccept  可获取到前一个任务的返回值,但是无返回值
        CompletableFuture<Void> thenAccept = seqFutureOne.thenAccept(name -> System.out.println(name + "thenAccept"));
        System.out.println("-------------");
        System.out.println(thenAccept.get());

        // thenRun 获取不到前一个任务的返回值,也无返回值
        System.out.println("-------------");
        CompletableFuture<Void> thenRun = seqFutureOne.thenRun(() -> System.out.println("thenRun"));
        System.out.println(thenRun.get());
    }

    /**
     * 运行结果:
     * 可以看到supplyAsync方法执行速度慢的话thenApply方法执行线程和supplyAsync执行线程相同，
     * 如果supplyAsync方法执行速度快的话，那么thenApply方法执行线程和Main方法执行线程相同。
     */
    @Test
    public void async() throws ExecutionException, InterruptedException {
        // thenApply和thenApplyAsync的区别
        System.out.println("-------------");
        CompletableFuture<String> supplyAsyncWithSleep = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "supplyAsyncWithSleep Thread Id : " + Thread.currentThread();
        });

        CompletableFuture<String> thenApply = supplyAsyncWithSleep
                .thenApply(name -> name + "------thenApply Thread Id : " + Thread.currentThread());
        CompletableFuture<String> thenApplyAsync = supplyAsyncWithSleep
                .thenApplyAsync(name -> name + "------thenApplyAsync Thread Id : " + Thread.currentThread());
        System.out.println("Main Thread Id: " + Thread.currentThread());
        System.out.println(thenApply.get());
        System.out.println(thenApplyAsync.get());

        System.out.println("-------------No Sleep");

        CompletableFuture<String> supplyAsyncNoSleep = CompletableFuture.supplyAsync(
                () -> "supplyAsyncNoSleep Thread Id : " + Thread.currentThread());
        CompletableFuture<String> thenApplyNoSleep = supplyAsyncNoSleep
                .thenApply(name -> name + "------thenApply Thread Id : " + Thread.currentThread());
        CompletableFuture<String> thenApplyAsyncNoSleep = supplyAsyncNoSleep
                .thenApplyAsync(name -> name + "------thenApplyAsync Thread Id : " + Thread.currentThread());
        System.out.println("Main Thread Id: " + Thread.currentThread());
        System.out.println(thenApplyNoSleep.get());
        System.out.println(thenApplyAsyncNoSleep.get());
    }

    /*
        thenCompose()：当第一个任务完成时才会执行第二个操作
        thenCombine()：两个异步任务全部完成时才会执行某些操作
     */
    @Test
    public void com() throws ExecutionException, InterruptedException {
        CompletableFuture<String> thenComposeComplete = getTaskOne().thenCompose(CompletableFutureTest::getTaskTwo);
        System.out.println(thenComposeComplete.get());

        // thenApply
        CompletableFuture<CompletableFuture<String>> thenApply = getTaskOne()
                .thenApply(CompletableFutureTest::getTaskTwo);
        System.out.println(thenApply.get().get());

        System.out.println("===================");
        // 例如我们此时需要计算两个异步方法返回值的和。求和这个操作是必须是两个异步方法得出来值的情况下才能进行计算
        CompletableFuture<Integer> thenComposeOne = CompletableFuture.supplyAsync(() -> 192);
        CompletableFuture<Integer> thenComposeTwo = CompletableFuture.supplyAsync(() -> 196);
        CompletableFuture<Integer> thenComposeCount = thenComposeOne
                .thenCombine(thenComposeTwo, Integer::sum);
        System.out.println(thenComposeCount.get());
    }

    /*
        allOf()：等待所有CompletableFuture完后以后才会运行回调函数
        anyOf()：只要其中一个CompletableFuture完成，那么就会执行回调函数。注意此时其他的任务也就不执行了。
     */
    @Test
    public void af() throws ExecutionException, InterruptedException {
        //allOf()
        CompletableFuture<Integer> one = CompletableFuture.supplyAsync(() -> 1);
        CompletableFuture<Integer> two = CompletableFuture.supplyAsync(() -> 2);
        CompletableFuture<Integer> three = CompletableFuture.supplyAsync(() -> 3);
        CompletableFuture<Integer> four = CompletableFuture.supplyAsync(() -> 4);
        CompletableFuture<Integer> five = CompletableFuture.supplyAsync(() -> 5);
        CompletableFuture<Integer> six = CompletableFuture.supplyAsync(() -> 6);

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(one, two, three, four, five, six);
        voidCompletableFuture.thenApply(v -> Stream.of(one, two, three, four, five, six)
                .map(CompletableFuture::join)
                .collect(Collectors.toList())).thenAccept(System.out::println);

        System.out.println("===================================");

        CompletableFuture<Void> voidCompletableFuture1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
            System.out.println("voidCompletableFuture1");
        });

        CompletableFuture<Void> voidCompletableFutur2 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {

            }
            System.out.println("voidCompletableFutur2");
        });

        CompletableFuture<Void> voidCompletableFuture3 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {

            }
            System.out.println("voidCompletableFuture3");
        });

        CompletableFuture<Object> objectCompletableFuture = CompletableFuture
                .anyOf(voidCompletableFuture1, voidCompletableFutur2, voidCompletableFuture3);
        objectCompletableFuture.get();
    }


    @Test
    public void exception() throws ExecutionException, InterruptedException {
        // 只要执行链中有一个发生了异常，那么接下来的链条也就不执行了，但是主流程下的其他CompletableFuture还是会运行的。
        CompletableFuture.supplyAsync(() -> {
            //发生异常
            int i = 10 / 0;
            return "Success";
        }).thenRun(() -> System.out.println("thenRun"))
                .thenAccept(v -> System.out.println("thenAccept"));

        CompletableFuture.runAsync(() -> System.out.println("CompletableFuture.runAsync"));

        //处理异常

        CompletableFuture<String> exceptionally = CompletableFuture.supplyAsync(() -> {
            //发生异常
            int i = 10 / 0;
            return "Success";
        }).exceptionally(e -> {
            System.out.println(e);
            return "Exception has Handle";
        }).thenApply(x -> {
            System.out.println("不会中断");
            return x + 1;
        });
        System.out.println(exceptionally.get());


        System.out.println("-------有异常-------");

        // 调用handle()方法也能够捕捉到异常并且自定义返回值，
        // 和exceptionally()方法不同一点是handle()方法无论发没发生异常都会被调用。例子如下
        CompletableFuture.supplyAsync(() -> {
            //发生异常
            int i = 10 / 0;
            return "Success";
        }).handle((response, e) -> {
            System.out.println("Exception:" + e);
            System.out.println("Response:" + response);
            return response;
        });

        System.out.println("-------无异常-------");
        CompletableFuture.supplyAsync(() -> "Success").handle((response, e) -> {
            System.out.println("Exception:" + e);
            System.out.println("Response:" + response);
            return response;
        });

    }

}
