package collection;

import java.util.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/25 19:27
 * 不支持的操作的常见来源是由固定大小的数据结构所支持的集合。使用 `Arrays.asList()` 方法将数组转换为
 * **List** 时，就会得到这样的集合。此外，还可以选择使用 **Collections** 类中的“不可修改（unmodifiable）”方法
 * 使任何集合（包括 **Map** ）抛出 **UnsupportedOperationException** 异常。
 * ---
 * 因为 `Arrays.asList()` 生成的 **List** 由一个固定大小的数组所支持，所以唯一支持的操作是那些不改变数组大小的操作。
 * 任何会导致更改基础数据结构大小的方法都会产生 **UnsupportedOperationException** 异常，来说明这是对不支持的方法的调用（编程错误）。
 * ---
 * 如果一个方法将一个集合作为它的参数，那么它的文档应该说明必须实现哪些可选方法。
 */
public class Unsupported {
    static void check(String description, Runnable tst) {
        try {
            tst.run();
        } catch (Exception e) {
            System.out.println(description + "(): " + e);
        }
    }

    static void test(String msg, List<String> list) {
        System.out.println("--- " + msg + " ---");
        Collection<String> c = list;
        Collection<String> subList = list.subList(1, 8);
        // Copy of the sublist:
        Collection<String> c2 = new ArrayList<>(subList);
        check("retainAll", () -> c.retainAll(c2));
        check("removeAll", () -> c.removeAll(c2));
        check("clear", c::clear);
        check("add", () -> c.add("X"));
        check("addAll", () -> c.addAll(c2));
        check("remove", () -> c.remove("C"));
        // The List.set() method modifies the value but
        // doesn't change the size of the data structure:
        check("List.set", () -> list.set(0, "X"));
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList(
                "A B C D E F G H I J K L".split(" "));
        test("Modifiable Copy", new ArrayList<>(list));
        test("Arrays.asList()", list);
        test("unmodifiableList()",
                Collections.unmodifiableList(
                        new ArrayList<>(list)));
    }
}
