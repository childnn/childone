package onjava8.patterns.strategy;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/23 19:27
 * *策略模式* 看起来像是从同一个基类继承而来的一系列 *命令* 类。
 * 但是仔细查看 *命令模式*，你就会发现它也具有同样的结构：一系列分层次的 *函数对象*。
 * 不同之处在于，这些函数对象的用法和策略模式不同。
 * *命令模式* 在编码阶段提供了灵活性，而 *策略模式* 的灵活性在运行时才会体现出来。尽管如此，这种区别却是非常模糊的。
 * ---
 * *策略模式* 还可以添加一个“上下文（context）”，这个上下文（context）可以是一个代理类（surrogate class），
 * 用来控制对某个特定 *策略* 对象的选择和使用。就像 *桥接模式* 一样！
 * ---
 * `MinimaSolver` 中的 `changeAlgorithm()` 方法将一个不同的策略插入到了 `私有` 域 `strategy` 中，
 * 这使得在调用 `minima()` 方法时，可以使用新的策略。
 */
public class StrategyPattern {
    public static void main(String[] args) {
        MinimaSolver solver = new MinimaSolver(new LeastSquares());
        List<Double> line = Arrays.asList(1.0, 2.0, 1.0, 2.0, -1.0, 3.0, 4.0, 5.0, 4.0);
        System.out.println(solver.minima(line));
        solver.changeAlgorithm(new Bisection());
        System.out.println(solver.minima(line));
    }
}

// The common strategy base type:
class FindMinima {
    Function<List<Double>, List<Double>> algorithm;
}

// The various strategies:
class LeastSquares extends FindMinima {
    LeastSquares() {
        // Line is a sequence of points (Dummy data):
        algorithm = (line) -> Arrays.asList(1.1, 2.2);
    }
}

class Perturbation extends FindMinima {
    Perturbation() {
        algorithm = (line) -> Arrays.asList(3.3, 4.4);
    }
}

class Bisection extends FindMinima {
    Bisection() {
        algorithm = (line) -> Arrays.asList(5.5, 6.6);
    }
}

// The "Context" controls the strategy:
class MinimaSolver {
    private FindMinima strategy;

    MinimaSolver(FindMinima strat) {
        strategy = strat;
    }

    List<Double> minima(List<Double> line) {
        return strategy.algorithm.apply(line);
    }

    void changeAlgorithm(FindMinima newAlgorithm) {
        strategy = newAlgorithm;
    }
}
