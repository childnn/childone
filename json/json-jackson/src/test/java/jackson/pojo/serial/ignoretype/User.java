package jackson.pojo.serial.ignoretype;

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
    private Student stu; // 该属性被忽略.
    private String name;

}
