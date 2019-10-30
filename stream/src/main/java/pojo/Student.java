package pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/22 16:39
 */
@Data
@Accessors(chain = true)
public class Student {
    private Integer age;
    private String name;
}
