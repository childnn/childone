package onjava8.patterns.chain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

interface Algorithm {
    Result algorithm(List<Double> line);
}

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/23 19:38
 * 我们从定义一个 `Result` 类开始，这个类包含一个 `success` 标志，因此接收者就可以知道算法是否成功执行，
 * 而 `line` 变量保存了真实的数据。当算法执行失败时， `Fail` 类可以作为返回值。
 * 要注意的是，当算法执行失败时，返回一个 `Result` 对象要比抛出一个异常更加合适，
 * 因为我们有时可能并不打算解决这个问题，而是希望程序继续执行下去。
 * 每一个 `Algorithm` 接口的实现，都实现了不同的 `algorithm()` 方法。在 `FindMinama` 中，
 * 将会创建一个算法的列表（这就是所谓的“链”），而 `minima()` 方法只是遍历这个列表，然后找到能够成功执行的算法而已。
 */
public class ChainOfResponsibility {
    public static void main(String[] args) {
        //FindMinima solver = new FindMinima();
        List<Double> line = Arrays.asList(
                1.0, 2.0, 1.0, 2.0, -1.0,
                3.0, 4.0, 5.0, 4.0);
        Result result = FindMinima.minima(line);
        if (result.success)
            System.out.println(result.line);
        else
            System.out.println("No algorithm found");
    }
}

class Result {
    boolean success;
    List<Double> line;

    Result(List<Double> data) {
        success = true;
        line = data;
    }

    Result() {
        success = false;
        line = Collections.emptyList();
    }
}

class Fail extends Result {
}

class FindMinima {
    static List<Function<List<Double>, Result>>
            algorithms = Arrays.asList(
            FindMinima::leastSquares,
            FindMinima::perturbation,
            FindMinima::bisection);

    public static Result leastSquares(List<Double> line) {
        System.out.println("LeastSquares.algorithm");
        boolean weSucceed = false;
        if (weSucceed) // Actual test/calculation here
            return new Result(Arrays.asList(1.1, 2.2));
        else // Try the next one in the chain:
            return new Fail();
    }

    public static Result perturbation(List<Double> line) {
        System.out.println("Perturbation.algorithm");
        boolean weSucceed = false;
        if (weSucceed) // Actual test/calculation here
            return new Result(Arrays.asList(3.3, 4.4));
        else
            return new Fail();
    }

    public static Result bisection(List<Double> line) {
        System.out.println("Bisection.algorithm");
        boolean weSucceed = true;
        if (weSucceed) // Actual test/calculation here
            return new Result(Arrays.asList(5.5, 6.6));
        else
            return new Fail();
    }

    public static Result minima(List<Double> line) {
        for (Function<List<Double>, Result> alg : algorithms) {
            Result result = alg.apply(line);
            if (result.success)
                return result;
        }
        return new Fail();
    }
}