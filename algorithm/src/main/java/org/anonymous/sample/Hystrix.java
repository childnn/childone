package org.anonymous.sample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CommandName     默认类名
 * CommandGroup   对应一个服务
 * CommandKey    对应一个服务下的一个接口
 * ThredPool    对应的线程池的名称  默认是类名  一般根据一个服务去划分一个线程池  （当然可以自己设置 同一个服务也多个线程池）
 * CoreSize      线程池大小 默认10个
 * QueueSize    队列大小   默认是5   可以适当调大一些  一旦积压超过5就直接reject
 * timeoutMill..  hystrix 自己本身超时的时间
 * 如果是Semphore    可以设置最大信号量
 * <p>
 * 短路器  可以设置短时间内 几次请求 有百分之五十的请求失败  短路器会打开
 * <p>
 * 执行流程  创建Command  --- 执行Command --- requestCache --- 短路器 --- 降级
 * <p>
 * .withCircuitBreakerRequestVolumeThreshold(30)//10秒内有30个请求时，触发断路器,可以控制流量
 * .withCircuitBreakerErrorThresholdPercentage(40)//当异常达到百分之40时，触发断路器
 * .withCircuitBreakerSleepWindowInMilliseconds(3000))//在3秒内直接refect请求走降级，3秒过后进入半开状态
 * );
 */
public class Hystrix {
    public static void main(String[] args) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        fixedThreadPool.submit(new LongTimeTask(1));
//        for (int i = 0; i < 10; i++) {
//            final int index = i;
//            fixedThreadPool.execute(new Runnable() {
//
//                @Override
//                public void run() {
//                    try {
//                        System.out.println(index);
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }

    }
}
