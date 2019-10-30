package org.anonymous.lombok.val;

import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/11 16:45
 */
public class ValExample {
    public static String example() {
        val example = new ArrayList<String>();
        example.add("hello, world!");
        val foo = example.get(0);

        return foo.toUpperCase();
    }

    public static void example2() {
        val map = new HashMap<Integer, String>();
        map.put(0, "Zero");
        map.put(3, "six");

        map.forEach((k, v) -> System.out.printf("%d: %s\n", k, v));
    }

    /*编译:
    *     public static String example() {
        ArrayList<String> example = new ArrayList();
        example.add("hello, world!");
        String foo = (String)example.get(0);
        return foo.toUpperCase();
    }

    public static void example2() {
        HashMap<Integer, String> map = new HashMap();
        map.put(0, "Zero");
        map.put(3, "six");
        map.forEach((k, v) -> {
            System.out.printf("%d: %s\n", k, v);
        });
    }*/

    public static void main(String[] args) {
        example2();
    }
}
