package jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/2 13:32
 * ---------------
 * .@State(Scope.Thread) 声明类实例是非线程共享的，且所有线程都实例话一个对象
 * .@State(Scope.Benchmark) 声明类实例是线程共享的，且所有线程共享一个实例
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
public class DoublingDemo {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(DoublingDemo.class.getSimpleName()).build();
        Collection<RunResult> run = new Runner(opt).run();
    }

    public int doubleIt(int n) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignore) {
            //e.printStackTrace();
        }
        return n * 2;
    }

    @Benchmark
    public int doubleAndSumSequential() {
        return IntStream.of(3, 1, 4, 1, 5, 9)
                .map(this::doubleIt)
                .sum();
    }

    @Benchmark
    public int doubleAndSumParallel() {
        return IntStream.of(3, 1, 4, 1, 5, 9)
                .parallel()
                .map(this::doubleIt)
                .sum();
    }
}
