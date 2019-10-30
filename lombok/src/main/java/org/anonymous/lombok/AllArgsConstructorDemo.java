package org.anonymous.lombok;

import lombok.AllArgsConstructor;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/11 14:48
 */
@AllArgsConstructor(staticName = "instance")
public class AllArgsConstructorDemo {
    private long id;

    private String name;

    private int age;

    public static void main(String[] args) {
        AllArgsConstructorDemo instance = instance(1L, "jack", 17);
    }
}
