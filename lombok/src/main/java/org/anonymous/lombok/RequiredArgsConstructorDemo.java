package org.anonymous.lombok;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/11 14:51
 */
@RequiredArgsConstructor
public class RequiredArgsConstructorDemo {

    @NonNull
    private Long id;

    private final String name;

    private int age;

    public static void main(String[] args) {
        RequiredArgsConstructorDemo rose = new RequiredArgsConstructorDemo(1L, "rose");
    }
}
