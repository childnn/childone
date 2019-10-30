package jackson.pojo.serial.ignore;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/22 17:43
 */
@Data
@Accessors(chain = true)
public class User {
    private Student stu;
    private String name;

}
