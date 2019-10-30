package org.anonymous.lombok;

import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/11 14:53
 */
@EqualsAndHashCode
public class EqualsAndHashCodeDemo {
    String firstName;
    String lastName;
    LocalDate dateOfBirth;

    public static void main(String[] args) {
        System.out.println("....");
    }
}
