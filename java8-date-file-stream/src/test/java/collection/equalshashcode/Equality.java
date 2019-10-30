package collection.equalshashcode;

import java.util.Objects;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/26 16:21
 */
public class Equality {
    protected int i;
    protected String s;
    protected double d;

    public Equality(int i, String s, double d) {
        this.i = i;
        this.s = s;
        this.d = d;
        System.out.println("made 'Equality'");
    }

    public static void testAll(EqualityFactory eqf) {
        Equality
                e = eqf.make(1, "Monty", 3.14),
                eq = eqf.make(1, "Monty", 3.14),
                neq = eqf.make(99, "Bob", 1.618);
        e.test("null", "false", null);
        e.test("same object", "true", e);
        e.test("different type",
                "false", 99);
        e.test("same values", "true", eq);
        e.test("different values", "false", neq);
    }

    public static void main(String[] args) {
        testAll(Equality::new);
    }

    // 上述的 **equals()** 函数非常繁琐，并且我们能够将其简化成规范的形式，请注意：
    // 1. **instanceof**检查减少了**null**检查的需要。
    // 2. 和**this**的比较是多余的。一个正确书写的 **equals()** 函数能正确地和自己比较。
    @Override
    public boolean equals(Object rval) {
        if (rval == null)
            return false;
        if (rval == this)
            return true;

        if (!(rval instanceof Equality))
            return false;

        Equality other = (Equality) rval;
        if (!Objects.equals(i, other.i))
            return false;
        if (!Objects.equals(s, other.s))
            return false;
        return Objects.equals(d, other.d);
    }

    public void test(String descr, String expected, Object rval) {
        System.out.format("-- Testing %s --%n" + "%s instanceof Equality: %s%n" +
                        "Expected %s, got %s%n",
                descr, descr, rval instanceof Equality,
                expected, equals(rval));
    }

}
