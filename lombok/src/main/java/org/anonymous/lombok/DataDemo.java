package org.anonymous.lombok;

import lombok.Data;

import java.time.LocalDate;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see lombok.ToString
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.RequiredArgsConstructor
 * @see lombok.EqualsAndHashCode
 * @since 2019/11/11 15:01
 * 等价于同时使用.
 */
@Data
public class DataDemo {
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
}
