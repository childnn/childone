package org.anonymous.demo;

import java.math.BigInteger;
import java.util.Formatter;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/16 21:45
 * 被注释的代码表示，针对相应类型的变量，这些转换是无效的。如果执行这些转换，则会触发异常。
 * 注意，程序中的每个变量都用到了 `b` 转换。虽然它对各种类型都是合法的，但其行为却不一定与你想象的一致。
 * 对于 `boolean` 基本类型或 `Boolean` 对象，其转换结果是对应的 `true` 或 `false`。但是，对其他类型的参数，
 * 只要该参数不为 `null`，其转换结果永远都是 `true`。即使是数字 0，转换结果依然为 `true`，而这在其他语言中（包括C），
 * 往往转换为 `false`。所以，将 `b` 应用于非布尔类型的对象时请格外小心。
 */
public class Conversion {
    public static void main(String[] args) {
        Formatter f = new Formatter(System.out);

        char u = 'a';
        System.out.println("u = 'a'");
        f.format("s: %s%n", u);
        // f.format("d: %d%n", u);
        f.format("c: %c%n", u);
        f.format("b: %b%n", u);
        // f.format("f: %f%n", u);
        // f.format("e: %e%n", u);
        // f.format("x: %x%n", u);
        f.format("h: %h%n", u);

        int v = 121;
        System.out.println("v = 121");
        f.format("d: %d%n", v);
        f.format("c: %c%n", v);
        f.format("b: %b%n", v);
        f.format("s: %s%n", v);
        // f.format("f: %f%n", v);
        // f.format("e: %e%n", v);
        f.format("x: %x%n", v);
        f.format("h: %h%n", v);

        BigInteger w = new BigInteger("50000000000000");
        System.out.println(
                "w = new BigInteger(\"50000000000000\")");
        f.format("d: %d%n", w);
        // f.format("c: %c%n", w);
        f.format("b: %b%n", w);
        f.format("s: %s%n", w);
        // f.format("f: %f%n", w);
        // f.format("e: %e%n", w);
        f.format("x: %x%n", w);
        f.format("h: %h%n", w);

        double x = 179.543;
        System.out.println("x = 179.543");
        // f.format("d: %d%n", x);
        // f.format("c: %c%n", x);
        f.format("b: %b%n", x);
        f.format("s: %s%n", x);
        f.format("f: %f%n", x);
        f.format("e: %e%n", x);
        // f.format("x: %x%n", x);
        f.format("h: %h%n", x);

        Conversion y = new Conversion();
        System.out.println("y = new Conversion()");

        // f.format("d: %d%n", y);
        // f.format("c: %c%n", y);
        f.format("b: %b%n", y);
        f.format("s: %s%n", y);
        // f.format("f: %f%n", y);
        // f.format("e: %e%n", y);
        // f.format("x: %x%n", y);
        f.format("h: %h%n", y);

        boolean z = false;
        System.out.println("z = false");
        // f.format("d: %d%n", z);
        // f.format("c: %c%n", z);
        f.format("b: %b%n", z);
        f.format("s: %s%n", z);
        // f.format("f: %f%n", z);
        // f.format("e: %e%n", z);
        // f.format("x: %x%n", z);
        f.format("h: %h%n", z);
    }
}
