package org.anonymous.lombok;

import lombok.NoArgsConstructor;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/11 14:45
 */
@NoArgsConstructor(staticName = "getInstance")
public class NoArgsConstructorDemo {

    private long id;

    private String name;

    private int age;

    public static void main(String[] args) {
        NoArgsConstructorDemo instance = getInstance();

    }
}
