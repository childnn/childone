package vavr;

import io.vavr.*;
import io.vavr.collection.*;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import io.vavr.test.Arbitrary;
import io.vavr.test.Property;
import org.junit.jupiter.api.Test;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see io.vavr.Tuple
 * @see io.vavr.Value
 * @see io.vavr.CheckedFunction0 可抛异常
 * @see io.vavr.Function0
 * @see io.vavr.control.Try
 * @see API#$
 * @see API#Case
 * @see API#Match
 * @see Predicates
 * @since 2021/8/10 15:12
 * https://docs.vavr.io/
 */
public class VavrPreview<T> {

    // We use uniform upper-case method names because 'case' is a keyword in Java. This makes the API special.
    @Test
    public void api() {
        // wildcard pattern
        API.Match.Pattern0<Object> $ = API.$();
        // equals pattern
        API.Match.Pattern0<Integer> $1 = API.$(1);
        // conditional pattern
        API.Match.Pattern0<Integer> $2 = API./*<Integer>*/$(x -> x == 1);

        // 给定任意值都返回 1 - retVal
        API.Match.Case<Object, Integer> aCase = API.Case($, 1);
        Integer apply = aCase.apply("31231");
        System.out.println("apply = " + apply);

        String s = API.Match(1)
                .of(
                        API.Case($2, "one"),
                        API.Case($2, "two"),
                        API.Case($2, "?")
                );
        System.out.println("s = " + s);
    }

    @Test
    public void propCheck() {
        Arbitrary<Integer> ints = Arbitrary.integer();

        // square(int) >= 0: OK, passed 1000 tests.
        Property.def("square(int) >= 0")
                .forAll(ints)
                .suchThat(i -> i * i >= 0)
                .check()
                .assertIsSatisfied();
    }

    /**
     * @see Traversable
     */
    @Test
    public void collection() {
        // 1000 random numbers
        for (double random : Stream.continually(Math::random).take(1000)) {
            // ...
        }

        System.out.println(Arrays.asList(1, 2, 3).stream().reduce(Integer::sum));
        System.out.println(IntStream.of(1, 2, 3).sum());

        // io.vavr.collection.List
        List.of(1, 2, 3).sum();

        // 2, 4, 6, ...
        Stream.from(1).filter(i -> i % 2 == 0);
    }

    @Test
    public void validate() {
        PersonValidator personValidator = new PersonValidator();

        // Valid(Person(John Doe, 30))
        Validation<Seq<String>, Person> valid = personValidator.validatePerson("John Doe", 30);

        System.out.println("valid = " + valid);

        // Invalid(List(Name contains invalid characters: '!4?', Age must be greater than 0))
        Validation<Seq<String>, Person> invalid = personValidator.validatePerson("John? Doe!4", -1);
        System.out.println("invalid = " + invalid);

    }

    class PersonValidator {

        private static final String VALID_NAME_CHARS = "[a-zA-Z ]";
        private static final int MIN_AGE = 0;

        public Validation<Seq<String>, Person> validatePerson(String name, int age) {
            return Validation.combine(validateName(name), validateAge(age)).ap(Person::new);
        }

        private Validation<String, String> validateName(String name) {
            return CharSeq.of(name).replaceAll(VALID_NAME_CHARS, "")
                    .transform(seq -> seq.isEmpty()
                            ? Validation.valid(name)
                            : Validation.invalid("Name contains invalid characters: '"
                            + seq.distinct().sorted() + "'"));
        }

        private Validation<String, Integer> validateAge(int age) {
            return age < MIN_AGE
                    ? Validation.invalid("Age must be at least " + MIN_AGE)
                    : Validation.valid(age);
        }

    }

    class Person {

        public final String name;
        public final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person(" + name + ", " + age + ")";
        }

    }

    /**
     * A Future is a computation result that becomes available at some point.
     * All operations provided are non-blocking. The underlying ExecutorService is
     * used to execute asynchronous handlers, e.g. via onComplete(…).
     * <p>
     * A Future has two states: pending and completed.
     * <p>
     * Pending: The computation is ongoing. Only a pending future may be completed or cancelled.
     * <p>
     * Completed: The computation finished successfully with a result, failed with an exception or was cancelled.
     */
    @Test
    public void future() {
        Future<Integer> of = Future.of(CheckedFunction0.constant(1));
    }

    @Test
    public void either() {
        Either<Object, Integer> vavr = Either.right(12).orElse(() -> Either.left("Vavr"));
        System.out.println(vavr.isLeft());
        // System.out.println(vavr.getLeft());
        System.out.println(vavr.get());
    }

    @Test
    public void lazy() {
        Lazy<Double> lazy = Lazy.of(Math::random);
        // = false
        System.out.println(lazy.isEvaluated());
        // = 0.123 (random generated)
        System.out.println(lazy.get());
        // = true
        System.out.println(lazy.isEvaluated());
        // = 0.123 (memoized)
        System.out.println(lazy.get());

        // !!! amazing
        // works only with interfaces
        CharSequence chars = Lazy.val(() -> "Yay!", CharSequence.class);
    }

    @Test
    public void map() {
        Tuple2<String, Integer> java8 = Tuple.of("Java", 8);
        Tuple2<String, Integer> map = java8.map(s -> s.substring(2) + "vr", i -> i / 8);
        System.out.println("map = " + map);
        Tuple2<String, Integer> map1 = java8.map((s, i) -> Tuple.of(s.substring(2) + "vr", i / 8));
        System.out.println("map1 = " + map1);
        String apply = java8.apply((s, i) -> s.substring(2) + "vr" + i / 8);
        System.out.println("apply = " + apply);
    }

    @Test
    public void tryTest() {
        Try<Integer> i = safeDivide(1, 0);
        System.out.println("i = " + i);
    }

    static Try<Integer> safeDivide(final int dividend, final int divisor) {
        return Try.of(() -> dividend / divisor);
    }

    static int divide(int dividend, int divisor) {
        // throws if divisor is zero
        return dividend / divisor;
    }

    @Test
    public void list() {

        // 链表
        List<Integer> list1 = List.of(1, 2, 3, 4);
        System.out.println("list1 = " + list1);
        List<Integer> list2 = List.Nil.<Integer>instance().prepend(4).prepend(3).prepend(2).prepend(1);
        System.out.println("list2 = " + list2);

        Map<Boolean, List<Integer>> tuple2s = list1.groupBy(x -> x % 2 == 0);
        System.out.println("tuple2s = " + tuple2s);
        Map<Integer, List<Integer>> tuple2s1 = list1.groupBy(x -> x % 2);
        System.out.println("tuple2s1 = " + tuple2s1);
        List<Tuple2<Integer, Integer>> tuple2s2 = list1.zipWithIndex();
        System.out.println("tuple2s2 = " + tuple2s2);
        List<Integer> integers = list1.zipWithIndex((x, i) -> x * i);
        System.out.println("integers = " + integers);
        // list1.zip()

        // List.Cons cons = new List.Cons(1, new List.Cons(2, new List.Cons(3, new List.Cons(4, List.Nil.instance()))));

    }

    /**
     * 队列
     *
     * @see Traversable#mkString(java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence)
     * @see Iterator#intersperse(java.lang.Object)
     */
    @Test
    public void queue() {
        Queue<Character> cq = Queue.fill(5, 'A');
        System.out.println("cq = " + cq);
        // 这个返回的是一个新的队列, 而不是在原队列基础上扩展
        // 从下面 dequeue 结果也可知
        System.out.println(cq.append('b').prepend('C')); // new Queue


        Tuple2<Character, Queue<Character>> dequeue = cq.dequeue();
        System.out.println("dequeue = " + dequeue);

        Option<Character> co = cq.dequeueOption().map(Tuple2::_1);
        Character c = co.getOrElse((Character) null);
        System.out.println("c = " + c);

    }

    @Test
    public void vavr() {
        // List.of().groupBy()
        Tuple2<String, Integer> tp = Tuple.of("8", 11);
        String s = tp._1();
        // tp.append()
        System.out.println("tp = " + tp);
        Tuple2<Integer, String> map = tp.map(Integer::parseInt, x -> x + "String");
        System.out.println("map = " + map);

        Tuple2<Integer, String> map1 = tp.map((v1, v2) -> Tuple.of(Integer.parseInt(v1) + 13123, "Java8"));
        System.out.println("map1 = " + map1);

        // tp.map1()

        String apply = tp.apply((v1, v2) -> v1 + v2);
        System.out.println("apply = " + apply);

        Function2<Integer, Integer, String> f = (v1, v2) -> v1 + "--" + v2;

        Function1<String, Integer> of = Function1.of(this::x);

        Function1<Collection<T>, T> of1 = Function1.of(this::f);

        // 执行 of 后, 把结果执行 andThen 后返回
        Function1<String, Integer> f1 = of.andThen(v1 -> v1 + 1);
    }

    @Test
    public void lift() {
        Function2<Integer, Integer, Integer> div = (a, b) -> a / b;
        Function2<Integer, Integer, Option<Integer>> safeDiv = Function2.lift(div);
        Option<Integer> x = safeDiv.apply(1, 0);
        System.out.println("x = " + x);
        System.out.println(x.getOrElse(-1));
        Option<Integer> n = safeDiv.apply(1, 3);
        System.out.println(n.getOrElse(-1));
        System.out.println("n = " + n);


        // 部分应用
        // 将 div 的参数一设置为默认值 100
        Function1<Integer, Integer> apply = div.apply(100);
        System.out.println(apply.apply(2));

        Function5<Integer, Integer, Integer, Integer, Integer, Integer> sum = (a, b, c, d, e) -> a + b + c + d + e;
        Function2<Integer, Integer, Integer> add5 = sum.apply(2, 3, 1);

        System.out.println(add5.apply(4, 9));


        // 柯里化
        Function1<Integer, Function1<Integer, Function1<Integer, Function1<Integer, Function1<Integer, Integer>>>>> curried = sum.curried();
        Function1<Integer, Function1<Integer, Function1<Integer, Function1<Integer, Integer>>>> apply1 = curried
                .apply(111);
        Function1<Integer, Function1<Integer, Function1<Integer, Integer>>> apply2 = apply1.apply(2);
        Function1<Integer, Function1<Integer, Integer>> apply3 = apply2.apply(3);
        Function1<Integer, Integer> apply4 = apply3.apply(4);
        Integer apply5 = apply4.apply(5);
        System.out.println("apply5 = " + apply5);

        // 柯里化最直观的优势: 参数复用, 比如 此例中复用第一个参数 111
        Integer apply6 = apply1.apply(100).apply(1).apply(2).apply(3);
        System.out.println("apply6 = " + apply6);
        // 其他优势, 待体会...

    }

    // 记忆: 缓存
    @Test
    public void memorized() {
        Function0<Double> hashCache =
                Function0.of(Math::random).memoized();

        double randomValue1 = hashCache.apply();
        double randomValue2 = hashCache.apply();

        System.out.println(randomValue1 == randomValue2);

    }

    @Test
    public void option() {
        Optional<String> maybeFoo = Optional.of("foo");

        // Java8-Optional 自带校验/转化, 不会 NPE
        Optional<String> maybeFooBar = maybeFoo.map(s -> (String) null)
                .map(s -> s.toUpperCase() + "bar");

        System.out.println(maybeFooBar);


        Option<String> foo = Option.of("foo");
        // 需要自己注意对 null 的处理
        Option<String> strings = foo.map(s -> (String) null)
                .flatMap(s -> Option.of(s).map(x -> x.toUpperCase() + "bar"));
        System.out.println("strings = " + strings);

        // 方式二
        Option<String> strings1 = foo.flatMap(s -> Option.of((String) null).map(x -> x.toLowerCase() + "bar"));
        System.out.println("strings1 = " + strings1);


        // NPE
        Option<String> s1 = foo.map(s -> (String) null)
                .map(s -> s.toUpperCase() + "bar");

    }

    public int x(String o) {

        return 0;
    }

    T f(Collection<T> list) {
        for (T t : list) {
            if (t instanceof String) {

            }
        }
        return null;
    }

    void m(Collection<String> list) {
        for (String t : list) {
            if (t != null) {

            }
        }
    }

    public static void main(String[] args) throws NoSuchMethodException {
        String methodName = "m";
        Method f = VavrPreview.class.getDeclaredMethod(methodName, Collection.class);
        ResolvableType rt = ResolvableType.forMethodParameter(f, 0);
        Class<?> gt = rt.getGeneric(0).resolve();
        System.out.println("gt = " + gt);
    }

}
