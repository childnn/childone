package org.anonymous.lombok;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/11 14:33
 * @see Getter#lazy() 延迟初始化, 当首次调用 getter 方法时进行初始化.
 *
 */
@Getter
@Setter
public class GetterAndSetterDemo {

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;
}
