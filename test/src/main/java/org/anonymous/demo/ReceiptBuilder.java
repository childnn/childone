package org.anonymous.demo;

import java.util.Formatter;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/16 21:42
 * ---
 * | 类型 | 含义               |
 * | :--: | :----------------- |
 * | `d`  | 整型（十进制）     |
 * | `c`  | Unicode字符        |
 * | `b`  | Boolean值          |
 * | `s`  | String             |
 * | `f`  | 浮点数（十进制）   |
 * | `e`  | 浮点数（科学计数） |
 * | `x`  | 整型（十六进制）   |
 * | `h`  | 散列码（十六进制） |
 * | `%`  | 字面值“%”          |
 */
public class ReceiptBuilder {
    private double total = 0;
    private Formatter f =
            new Formatter(new StringBuilder());

    public ReceiptBuilder() {
        f.format(
                "%-15s %5s %10s%n", "Item", "Qty", "Price");
        f.format(
                "%-15s %5s %10s%n", "----", "---", "-----");
    }

    public static void main(String[] args) {
        ReceiptBuilder receiptBuilder =
                new ReceiptBuilder();
        receiptBuilder.add("Jack's Magic Beans", 4, 4.25);
        receiptBuilder.add("Princess Peas", 3, 5.1);
        receiptBuilder.add(
                "Three Bears Porridge", 1, 14.29);
        System.out.println(receiptBuilder.build());
    }

    public void add(String name, int qty, double price) {
        f.format("%-15.15s %5d %10.2f%n", name, qty, price);
        total += price * qty;
    }

    public String build() {
        f.format("%-15s %5s %10.2f%n", "Tax", "",
                total * 0.06);
        f.format("%-15s %5s %10s%n", "", "", "-----");
        f.format("%-15s %5s %10.2f%n", "Total", "",
                total * 1.06);
        return f.toString();
    }
}
