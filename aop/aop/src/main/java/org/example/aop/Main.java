package org.example.aop;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2023/6/29
 */
public class Main {

    // 长期支持版本: jdk11, jdk17
    public static void main(String[] args) {
        System.out.println("Hello world!");
        swch("3");
        str();
        // npe();
        Address jack = new Address(1, "jack");
        int age = jack.age();
        String name = jack.name();
    }

    /**
     * switch 新特性
     * jdk12, 2019-03
     * https://openjdk.org/jeps/325
     */
    public static void swch(String s) {
        switch (s) {
            case "1" -> System.out.println(s);
            case "2" -> {
                int i = 1 + 1;
                System.out.println(i);
            }
            case "3", "4", "5" -> System.out.println("xxxx");
            default -> throw new RuntimeException("default error");
        }

        String result = switch (s) {
            case "1" -> "2";
            case "2", "3", "4" -> "aaa";
            case "5" -> "xxx";
            default -> throw new IllegalStateException("Unexpected value: " + s);
        };

    }

    /**
     * jdk13, 2019-09
     * 字符串换行优化: 文本域
     */
    public static void str() {
        String s = """
                可优雅
                换行的
                文本域
                """;
        System.out.println(s);
    }

    /**
     * jdk14, 2020-03
     * instanceof 优化
     */
    public static void inOf(Object o) {
        if (o instanceof String s) {
            System.out.println(s);
        }
    }

    /**
     * jdk14, 2020-03
     * NPE 信息优化
     */
    public static void npe() {
        Object o = null;
        // java.lang.NullPointerException: Cannot invoke "Object.toString()" because "o" is null
        System.out.println(o.toString().getBytes());
    }

}

/**
 * record: 取代 lombok
 * jdk15, 2020-09
 *
 * @see java.lang.Record
 */
record Address(int age, String name) {
}