package onjava8.patterns.strategy;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/23 19:32
 */
public class StrategyPattern2 {
    public static void main(String[] args) {
        FindMinima2 solver = new FindMinima2();
        List<Double> line = Arrays.asList(
                1.0, 2.0, 1.0, 2.0, -1.0,
                3.0, 4.0, 5.0, 4.0);
        System.out.println(solver.minima(line));
        solver.bisection();
        System.out.println(solver.minima(line));
    }
}

// 将上下文注入到 `FindMinima` 中来简化我们的解决方法
// `FindMinima2` 封装了不同的算法，也包含了“上下文”（Context），所以它便可以在一个单独的类中控制算法的选择了。
// "Context" is now incorporated:
class FindMinima2 {
    Function<List<Double>, List<Double>> algorithm;

    FindMinima2() {
        leastSquares();
    } // default

    // The various strategies:
    void leastSquares() {
        algorithm = (line) -> Arrays.asList(1.1, 2.2);
    }

    void perturbation() {
        algorithm = (line) -> Arrays.asList(3.3, 4.4);
    }

    void bisection() {
        algorithm = (line) -> Arrays.asList(5.5, 6.6);
    }

    List<Double> minima(List<Double> line) {
        return algorithm.apply(line);
    }
}