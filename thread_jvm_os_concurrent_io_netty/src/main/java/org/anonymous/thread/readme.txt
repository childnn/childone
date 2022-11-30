java.lang.Thread#yield  static native
java.lang.Thread#join   final synchronized
java.lang.Thread#sleep  static native
java.lang.Object#wait   final native

yield 与 sleep 类似, 但不会阻塞当前线程, 只是将当前线程转入就绪(RUNNABLE)状态(sleep 是 BLOCKED 状态)
   让当前线程暂停一下, 让系统的线程调度器重新调度一次, 完全可能情况是: 当某个线程调用了 yield() 方法暂停后,
   线程调度器又将其调度出来重新执行.
   实际上, 当某个线程调用 yield() 方法暂停后, 只有优先级与当前线程相同或优先级更高的处于 RUNNABLE 状态的线程才会
   获得执行的机会.

join: Waits for this thread to die.
 join 可以指定等待时间.
 join 调用 wait 方法,

sleep: The thread does not lose ownership of any monitors.
wait: 对应 notify, 释放锁