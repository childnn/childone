package org.anonymous.lombok.builder;

import lombok.Builder;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/11 15:24
 */
@Builder
public class BuilderDemo {
    private final String firstName;
    private final String lastName;
    private final String email;

    public static void main(String[] args) {
        BuilderDemo demo = BuilderDemo.builder()
                .firstName("Child")
                .lastName("Jack")
                .email("27497963@qq.com")
                .build();
    }
}
