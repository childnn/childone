package jackson.pojo.serial.ignoretype;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/22 17:00
 */
@Data
@Accessors(chain = true)
@JsonIgnoreType // 被其他类引用时, 忽略. -- 不会忽略当前类的属性, 当前类在序列化时不受影响.
public class Student {
    private int id;
    private String name;
    private String sex;
}
