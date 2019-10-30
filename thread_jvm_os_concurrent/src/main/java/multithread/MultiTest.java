package multithread;

import org.junit.jupiter.api.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/1 19:39
 * 一个 main 函数启动, 是 main 线程和其他多个线程同时运行.
 */
public class MultiTest {
    @Test
    public void test() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] infos = bean.dumpAllThreads(false, false);
        for (ThreadInfo info : infos) {
            System.out.println(info.getThreadId() + ": " + info.getThreadName() + ": " + Arrays.toString(info.getStackTrace()));
        }
    }
}
